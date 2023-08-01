#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sysexits.h>
#include <unistd.h>

#include "command.h"
#include "executor.h"

#define DEF_MODE 0666

int execute_helper(struct tree *t, int fd_in, int fd_out);

int execute(struct tree *t) {
  if (t == NULL) {
    return 0;
  } else {
    return execute_helper(t, STDIN_FILENO, STDOUT_FILENO);
  }
}

/* Helper function that simplifies the this functions recursive call  */
int execute_helper(struct tree *t, int fd_in, int fd_out) {
  pid_t pid;
  int open_ret_in, open_ret_out, chdir1, chdir2, wait_ret, pipe_fd[2];
  /* Handles case of no conjunctions    */
  if (t->conjunction == NONE) {

    if (strcmp(t->argv[0], "exit") == 0) {
      exit(EXIT_SUCCESS);
    } else if (strcmp(t->argv[0], "cd") == 0) {
      /*  If the second element in argv is NULL, change to the home directory */
      if (t->argv[1] == NULL) {
        chdir1 = chdir(getenv("HOME"));
        if (chdir1 == -1) {
          perror("HOME");
          fflush(stderr);
        }
        /*  Changes the directory to the specified directory in argv[1]  */
      } else {
        chdir2 = chdir(t->argv[1]);
        if (chdir2 == -1) {
          fprintf(stderr, "%s", t->argv[1]);
          fflush(stderr);
        }
      }
      /* Commands other than cd and exit need to be handled in another program.
       * To do this, call fork()  and exec* */
    } else {
      /* Creates a parent-child relationship between processes */
      pid = fork();
      if (pid == -1) {
        perror("Fork Error");
        fflush(stdout);
      } else if (pid == 0) { /*  Child process code  */
        if (t->input != NULL) {
          open_ret_in =
              open(t->input, O_RDONLY); /*  Opens t->input for reading  */
          if (open_ret_in == -1) {
            perror("Opening input failed.\n");
            fflush(stderr);
          }
          if (dup2(open_ret_in, STDIN_FILENO) ==
              -1) { /* Redirects input to stdin  */
            perror("dup2 failed");
            fflush(stderr);
          }
          if (close(open_ret_in) == -1) {
            perror("Closing input failed.\n");
            fflush(stderr);
          }
        }
        if (t->output != NULL) {
          /*  Opens t->output witht the specified bitwise operations on the
           * flags   */
          open_ret_out =
              open(t->output, O_WRONLY | O_TRUNC | O_CREAT, DEF_MODE);
          if (open_ret_out == -1) {
            perror("Opening output failed.\n");
            fflush(stderr);
          }
          if (dup2(open_ret_out, STDOUT_FILENO) ==
              -1) { /* Redirects output to stdout  */
            perror("dup2 failed");
            fflush(stderr);
          }
          if (close(open_ret_out) == -1) {
            perror("Closing output failed.\n");
            fflush(stderr);
          }
        }
        /* Execute the specified command in argv with execvp function  */
        execvp(t->argv[0], t->argv);
        fprintf(stderr, "Failed to execute %s", t->argv[0]);
        exit(EXIT_FAILURE);
        /*  Parent process. Waits to reap children and returns the wait status*/
      } else {
        wait(&wait_ret);
        return wait_ret;
      }
    }
    /*  Handles case of AND  */
  } else if (t->conjunction == AND) {
    if (t->input != NULL) {
      open_ret_in = open(t->input, O_RDONLY); /*  Opens t->input for reading */
      if (open_ret_in == -1) {
        perror("Opening input failed.\n");
        fflush(stderr);
      }
    }
    if (t->output != NULL) {
      /*  Opens t->output with options from result of bitwise operations on
       * flags */
      open_ret_out = open(t->output, O_WRONLY | O_TRUNC | O_CREAT, DEF_MODE);
      if (open_ret_out == -1) {
        perror("Opening output failed.\n");
        fflush(stderr);
      }
    }
    /*  Recursive calls for both sides of children for a process. Firsts calls
     * it on left subtree, and if it executes correctly call on right subtree */
    if (execute_helper(t->left, fd_in, fd_out) == 0) {
      execute_helper(t->right, fd_in, fd_out);
    }
    /* Conjunction is SUBSHELL */
  } else if (t->conjunction == SUBSHELL) {
    if (t->input != NULL) {
      /*  Open t->input for reading  */
      open_ret_in = open(t->input, O_RDONLY);
      if (open_ret_in == -1) {
        perror("Opening input failed.\n");
        fflush(stderr);
      }
    }
    if (t->output != NULL) {
      /* Opens t->output with options from result of bitwise operations of the
       * three flags */
      open_ret_out = open(t->output, O_WRONLY | O_TRUNC | O_CREAT, DEF_MODE);
      if (open_ret_out == -1) {
        perror("Opening output file failed.\n");
        fflush(stderr);
      }
    }
    /* To handle SUBSHELL, fork() is needed  */
    pid = fork();
    if (pid == 0) { /*  Child case, which recursivley calls itself with the two
                       arguments given in the function on the left subtree  */
      execute_helper(t->left, fd_in, fd_out);
      exit(EXIT_SUCCESS);
    } else if (pid > 0) { /* Parent case, waits to reap children  */
      wait(&wait_ret);
    } else {
      perror("Fork error.\n");
      fflush(stderr);
    }
    /*  Handles the PIPE case  */
  } else if (t->conjunction == PIPE) {
    /*  If left has output or right has input, input/output is ambigious  */
    if (t->left->output == NULL && t->right->input == NULL) {
      if (t->input != NULL) {
        /* Opens t->input for reading  */
        open_ret_in = open(t->input, O_RDONLY);
        if (open_ret_in == -1) {
          perror("Opening input failed.\n");
          fflush(stderr);
        }
      }
      if (t->output != NULL) {
        /* Opens t->output based of result of bitwise operation on flags  */
        open_ret_out = open(t->output, O_WRONLY | O_TRUNC | O_CREAT, DEF_MODE);
        if (open_ret_out == -1) {
          perror("Opening output failed.\n");
          fflush(stderr);
        }
      }
      /* Calling pipe(), and passing in pipe_fd array which contains the read
       * side in pipe_fd[0] and the write side in pipe_fd[1]  */
      if (pipe(pipe_fd) != -1) {
        /*  Call fork() to have parent and child communcation between processes
         */
        pid = fork();
        if (pid == -1) {
          perror("Fork error.\n");
          fflush(stderr);
          /*  Parent case: close the write end of the pipe and redirect the
           * input to stdin using dup2(). Call the recursive function on the
           * right subtree, with input coming from stdin, and output to fd_out.
           * Waits for child proccesses to finish. */
        } else if (pid > 0) {
          close(pipe_fd[1]);
          if (dup2(pipe_fd[0], STDIN_FILENO) == -1) {
            perror("dup2 failed");
          }
          execute_helper(t->right, STDIN_FILENO, fd_out);
          close(pipe_fd[0]);
          wait(&wait_ret);
          /* Child case: close the reading end of the pipe. Redirect output from
           * pipe_fd[1] to stdout using dup2(). Call the recursive function on
           * the left subtree using fd_in as the input and stdout as the output.
           */
        } else {
          close(pipe_fd[0]);
          if (dup2(pipe_fd[1], STDOUT_FILENO) == -1) {
            perror("dup2 failed");
          }
          execute_helper(t->left, fd_in, STDOUT_FILENO);
          close(pipe_fd[1]);
          exit(EXIT_SUCCESS);
        }

      } else {
        perror("Pipe failed.\n");
        fflush(stdout);
      }
      /* Handles ambigious input/output */
    } else {
      if (t->right->input != NULL) {
        printf("Ambiguous input redirect.\n");
        fflush(stdout);

      } else {
        printf("Ambiguous output redirect.\n");
        fflush(stdout);
      }
    }
  }

  return 0;
}

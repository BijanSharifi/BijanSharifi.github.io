#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sysexits.h>
#include <errno.h>
#include <err.h>

#define MAX_LINE_LENGTH 1024


int main(){
  int fork_ret, execvp_ret, chdir_ret;
  char string[MAX_LINE_LENGTH], command[MAX_LINE_LENGTH], dest[MAX_LINE_LENGTH], *command_line[MAX_LINE_LENGTH], third[MAX_LINE_LENGTH];
  

  printf("shell_jr: ");
  fflush(stdout);

  while(fgets(string, MAX_LINE_LENGTH+1, stdin)){
   

    
    sscanf(string, "%s %s %s", command, dest, third);

     command_line[0]=command;
        if(strcmp(dest, "")!=0){
          command_line[1]=dest;
          command_line[2]=NULL;
        }else{
          command_line[1]=NULL;
        }

    
if(strcmp(command, "cd")==0){
      chdir_ret=chdir(dest);
      if(chdir_ret==-1){
        printf("Cannot change to directory <%s>", dest);
        fflush(stdout);
        exit(EX_OSERR);
      }
    }else if(strcmp(command, "hastalavista")==0 || strcmp(command, "exit")==0){
      printf("See you\n");
      fflush(stdout);
      exit(EXIT_SUCCESS);
    }else{
      
      fork_ret=fork();
  
 
      if(fork_ret==-1){
        printf("fork error");
        fflush(stdout);
        exit(EX_OSERR);
        }else if(fork_ret==0){
        
      
      execvp(command_line[0], command_line);
      printf("Failed to execute %s", command);
         fflush(stdout);
         exit(EX_OSERR);
         
       }else{
       wait(NULL);
       }
       
        
        }
   
    printf("shell_jr: ");
    fflush(stdout);
    
    }
   

    
  
  exit(EXIT_SUCCESS);
  return 0;
  
  
  
  
}


#include <ctype.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sysexits.h>

#define DOCUMENT_H
#define MAX_PARAGRAPH_LINES 20
#define MAX_PARAGRAPHS 15
#define MAX_STR_SIZE 80
#define HIGHLIGHT_START_STR "["
#define HIGHLIGHT_END_STR "]"
#define SUCCESS 0
#define FAILURE -1
#define MAX_LINE 1024

typedef struct {
  int number_of_lines;
  char lines[MAX_PARAGRAPH_LINES][MAX_STR_SIZE + 1];
} Paragraph;

typedef struct {
  char name[MAX_STR_SIZE + 1];
  int number_of_paragraphs;
  Paragraph paragraphs[MAX_PARAGRAPHS];
} Document;

int init_document(Document *doc, const char *name);
int reset_document(Document *doc);
int print_document(Document *doc);
int add_paragraph_after(Document *doc, int paragraph_number);
int add_line_after(Document *doc, int paragraph_number, int line_number,
                   const char *new_line);
int get_number_lines_paragraph(Document *doc, int paragraph_number,
                               int *number_of_lines);
int append_line(Document *doc, int paragraph_number, const char *new_line);
int remove_line(Document *doc, int paragraph_number, int line_number);
int load_document(Document *doc, char data[][MAX_STR_SIZE + 1], int data_lines);
int replace_text(Document *doc, const char *target, const char *replacement);
int highlight_text(Document *doc, const char *target);
int remove_text(Document *doc, const char *target);
int load_file(Document *doc, const char *filename);
int save_document(Document *doc, const char *filename);
static int valid_input(char *string);
static int is_empty(char *string);

int main(int argc, char *argv[]) {
  FILE *input;
  Document doc;
  char curr_line[MAX_LINE + 1], function[MAX_STR_SIZE + 1],
      paragraph[MAX_STR_SIZE + 1], line[MAX_STR_SIZE + 1],
      new_line[MAX_STR_SIZE + 1], *line_ptr, extra[MAX_STR_SIZE + 1],
      file_name[MAX_STR_SIZE + 1], target[MAX_STR_SIZE + 1],
      replacement[MAX_STR_SIZE + 1], *tar_start_ptr, *tar_end_ptr,
      *rep_start_ptr, *rep_end_ptr, *target_ptr, *rep_ptr;
  int length, is_blank = 1, i, num_arg, paragraph_number, line_number,
              std_input = 0, num_promps, extra_int;

  /* Checks if input is standard input or not. If it is print ">" prompt. If its
   * from an external file, open file for reading. If anything goes wrong error
   * message gets printed to stderr and it is exited.
   */
  if (argc == 1) {
    printf(">");
    input = stdin;
    std_input = 1;
  } else if (argc == 2) {
    input = fopen(argv[1], "r");
    if (input == NULL) {
      fprintf(stderr, "%s cannot be opened.\n", argv[1]);
      exit(EX_OSERR);
    }
  } else {
    fprintf(stderr, "Usage: user_interface\n");
    fprintf(stderr, "Usage: user_interface <filename>\n");
    exit(EX_USAGE);
  }
  /* Initializes document     */
  init_document(&doc, "main_document");
  /* Loop that reads in input until the document is ended.    */
  while (fgets(curr_line, MAX_LINE + 1, input)) {

    /* Find out what function the command is calling */
    sscanf(curr_line, "%s", function);
    /*  If the line is empty or a comment it doesn't need to do anything  */
    if (is_empty(curr_line) == FAILURE && *function != '#') {

      if (strcmp(function, "add_paragraph_after") == 0) {
        /* Scans in the paragraph from the command. Adds extra variable to make
         * sure seg fault is not generated with extra input. If the paragraph is
         * not a number, command is invalid. If the number of arguments is
         * correct, and the paragraph number is greater than zero, a paragraph
         * gets added.  */
        num_arg = sscanf(curr_line, "%s %s %s", function, paragraph, extra);
        if (valid_input(paragraph) == FAILURE) {
          printf("Invalid Command\n");
        } else {
          paragraph_number = atoi(paragraph);
          if (num_arg == 2 && paragraph_number >= 0) {
            if (add_paragraph_after(&doc, paragraph_number) == FAILURE) {
              printf("add_paragraph_after failed\n");
            } else {
            }
          } else {
            printf("Invalid Command\n");
          }
        }

      } else if (strcmp(function, "add_line_after") == 0) {
        /* Scans in paragraph, the linenumber, and the newline. Assign a pointer
         * to the newline. The end newline character needs to be be eliminated
         * so it becomes a null byte instead. If the paragraph and line are both
         * valid the line is added, but only after the line_ptr is incramented
         * by one because we don't want to include the * that was in the
         * command. */

        sscanf(curr_line, "%s %s %s %s", function, paragraph, line, new_line);

        line_ptr = strstr(curr_line, new_line);
        line_ptr[strlen(line_ptr) - 1] = '\0';

        if (valid_input(paragraph) == FAILURE || valid_input(line) == FAILURE) {
          printf("Invalid Command\n");
        } else {

          paragraph_number = atoi(paragraph);
          line_number = atoi(line);
          if (paragraph_number > 0 && line_number >= 0 && *line_ptr == '*') {

            if (add_line_after(&doc, paragraph_number, line_number,
                               ++line_ptr) == FAILURE) {
              printf("add_line_after failed\n");
            }
          } else {
            printf("Invalid Command\n");
          }
        }
      } else if (strcmp(function, "print_document") == 0) {
        /* Prints the document. Checks to make sure there isn't extra input.*/

        num_arg = sscanf(curr_line, "%s %s", function, extra);
        if (num_arg == 1) {

          print_document(&doc);
        } else {
          printf("Invalid Command\n");
        }
      } else if (strcmp(function, "exit") == 0 ||
                 strcmp(function, "quit") == 0) {
        /*  Exits the program if the command quit or exit is entered. Checks for
         * extra arguments.     */
        num_arg = sscanf(curr_line, "%s %s", function, extra);
        if (num_arg == 1) {
          exit(EXIT_SUCCESS);
        } else {
          printf("Invalid Command\n");
        }

      } else if (strcmp(function, "append_line") == 0) {
        /* Similar to add line after, except calls append_line. */
        sscanf(curr_line, "%s %s %s", function, paragraph, new_line);
        line_ptr = strstr(curr_line, new_line);
        line_ptr[strlen(line_ptr) - 1] = '\0';

        if (valid_input(paragraph) == FAILURE) {
          printf("Invalid Command\n");
        } else {
          paragraph_number = atoi(paragraph);
          if (paragraph_number > 0 && *line_ptr == '*') {
            line_ptr++;
            if (append_line(&doc, paragraph_number, line_ptr) == FAILURE) {
              printf("append_line failed\n");
            }
          } else {
            printf("Invalid Command\n");
          }
        }

      } else if (strcmp(function, "remove_line") == 0) {
        /*  Checks for extra input. If all input is fine, remove the line based
         * on the specified paragraph and line number.     */
        num_arg =
            sscanf(curr_line, "%s %s %s %s", function, paragraph, line, extra);
        if (valid_input(paragraph) == FAILURE || valid_input(line) == FAILURE) {
          printf("Invalid Command\n");
        } else {
          paragraph_number = atoi(paragraph);
          line_number = atoi(line);

          if (num_arg == 3 && paragraph_number > 0 && line_number > 0) {
            if (remove_line(&doc, paragraph_number, line_number) == FAILURE) {
              printf("remove_line failed\n");
            }
          } else {
            printf("Invalid Command\n");
          }
        }

      } else if (strcmp(function, "load_file") == 0) {
        /*   Loads the file and checks for extra input. */
        num_arg = sscanf(curr_line, "%s %s %s", function, file_name, extra);
        if (num_arg != 2) {
          printf("Invalid Command\n");
        } else {
          if (load_file(&doc, file_name) == FAILURE) {
            printf("load_file failed\n");
          }
        }

      } else if (strcmp(function, "replace_text") == 0) {
        /*  If the number of arguments is not 3, the command is invalid. */
        num_arg = sscanf(curr_line, "%s %s %s", function, target, replacement);

        if (num_arg != 3) {
          printf("Invalid Command\n");
        } else {
          /* Finds the first occurance of quotes in the target word.  */
          tar_start_ptr = strstr(curr_line, "\"");

          if (tar_start_ptr == NULL) {
            printf("Invalid Command\n");
          }
          /*  Finds the ending quotes on the target word.  */
          tar_end_ptr = strstr(tar_start_ptr + 1, "\"");

          if (tar_end_ptr == NULL) {
            printf("Invalid Command\n");
          }
          /*  Find the first quotes in the replacement word.  */
          rep_start_ptr = strstr(tar_end_ptr + 1, "\"");

          if (rep_start_ptr == NULL) {
            printf("Invalid Command\n");
          }
          /*  Finds the ending quotes on the replacement word.  */
          rep_end_ptr = strstr(rep_start_ptr + 1, "\"");

          if (rep_end_ptr == NULL) {
            printf("Invalid Command\n");
          }
          /*  Checks if any of the pointers are null. They are null if it can't
           * find a quote where its suppose to be.  */
          if (tar_start_ptr != NULL && tar_end_ptr != NULL &&
              rep_start_ptr != NULL && rep_end_ptr != NULL) {

            /* Coppies the difference of the two points to target and
             * replacement, which are the correct words that don't contain the
             * quotes. Also, null byte is added to end instead of newline
             * character. */
            strncpy(target, tar_start_ptr + 1, tar_end_ptr - tar_start_ptr);
            target[tar_end_ptr - (tar_start_ptr + 1)] = '\0';
            strncpy(replacement, rep_start_ptr + 1,
                    rep_end_ptr - rep_start_ptr);
            replacement[rep_end_ptr - (rep_start_ptr + 1)] = '\0';
            if (replace_text(&doc, target, replacement) == FAILURE) {
              printf("replace_text failed\n");
            }
          }
        }

      } else if (strcmp(function, "highlight_text") == 0) {
        /* Similar to replace text, but only takes in a target.  */
        num_arg = sscanf(curr_line, "%s %s", function, target);
        if (num_arg != 2) {
          printf("Invalid Command\n");
        } else {
          tar_start_ptr = strstr(curr_line, "\"");
          if (tar_start_ptr == NULL) {
            printf("Invalid Command\n");
          }
          tar_end_ptr = strstr(tar_start_ptr + 1, "\"");
          if (tar_end_ptr == NULL) {
            printf("Invalid Command\n");
          }

          if (tar_start_ptr != NULL && tar_end_ptr != NULL) {
            strncpy(target, tar_start_ptr + 1, tar_end_ptr - tar_start_ptr);
            target[tar_end_ptr - (tar_start_ptr + 1)] = '\0';
            if (highlight_text(&doc, target) == FAILURE) {
              printf("highlight_text failed\n");
            }
          }
        }

      } else if (strcmp(function, "remove_text") == 0) {
        /*  Similar to highlgight text except calls remove line. */
        num_arg = sscanf(curr_line, "%s %s", function, target);
        if (num_arg != 2) {
          printf("Invalid Command\n");
        } else {
          tar_start_ptr = strstr(curr_line, "\"");
          if (tar_start_ptr == NULL) {
            printf("Invalid Command\n");
          }
          tar_end_ptr = strstr(tar_start_ptr + 1, "\"");
          if (tar_end_ptr == NULL) {
            printf("Invalid Command\n");
          }

          if (tar_start_ptr != NULL && tar_end_ptr != NULL) {
            strncpy(target, tar_start_ptr + 1, tar_end_ptr - tar_start_ptr);
            target[tar_end_ptr - (tar_start_ptr + 1)] = '\0';
            if (remove_text(&doc, target) == FAILURE) {
              printf("remove_text failed\n");
            }
          }
        }

      } else if (strcmp(function, "save_document") == 0) {
        /*  Saves the document to another file. Checks for extra arguments.   */
        num_arg = sscanf(curr_line, "%s %s %s", function, file_name, extra);
        if (num_arg != 2) {
          printf("Invalid Command\n");
        } else {
          if (save_document(&doc, file_name) == FAILURE) {
            printf("save_document failed\n");
          }
        }

      } else if (strcmp(function, "reset_document") == 0) {
        /*  Resets the document. Checks for extra input. */
        num_arg = sscanf(curr_line, "%s %s", function, extra);
        if (num_arg != 1) {
          printf("Invalid Command\n");
        } else {
          reset_document(&doc);
        }
        /* Prints Invalid Command if it is not one of the commands above. */
      } else {
        printf("Invalid Command\n");
      }
    }
    /* Keeps printing the prompt as long as it is standard input*/
    if (std_input == 1) {
      printf(">");
    }
  }
}
/* Helper function that makes sure the string that is passed in can be
 * represented as a number. Uses isdigit function on each character of the
 * string. */
static int valid_input(char *string) {
  int length = strlen(string), i;
  for (i = 0; i < length; i++) {
    if (!isdigit(string[i])) {
      return FAILURE;
    }
  }
  return SUCCESS;
}
/* Checks to see if the line is empty. If it is a newline character to start or
 * a nullbyte to start the line is empty. Otherwise the string is looped
 * through. If there is one character that is not a space, it returns zero and
 * the function returns failure, letting us know its not a blank line. */
static int is_empty(char *string) {
  int length = strlen(string), i;
  if (*string == '\n' || *string == '\0') {
    return SUCCESS;
  } else {
    for (i = 0; i < length; i++) {
      if (isspace(string[i]) == 0) {
        return FAILURE;
      }
    }
    return SUCCESS;
  }
}

/*
Bijan Sharifi
117814781
bsharifi
 */

/*
Initializes the document by giving it a name and number of paragraphs set to
zero
  */
int init_document(Document *doc, const char *name) {
  if (doc == NULL || name == NULL || strlen(name) > MAX_STR_SIZE) {
    return FAILURE;
  } else {
    doc->number_of_paragraphs = 0;
    strcpy(doc->name, name);
    return SUCCESS;
  }
}
/*
Sets number of paragraphs on a doc to zero
  */
int reset_document(Document *doc) {
  if (doc == NULL) {
    return FAILURE;
  } else {
    doc->number_of_paragraphs = 0;
    return SUCCESS;
  }
}
/*
Prints the document by traversing through the paragraphs of the doc and each
individual lines
  */
int print_document(Document *doc) {
  int i, j, num_para = doc->number_of_paragraphs, num_lines;
  if (doc == NULL) {
    return FAILURE;
  } else {
    printf("Document name: \"%s\"\n", doc->name);
    printf("Number of Paragraphs: %d\n", num_para);
    for (i = 0; i < num_para; i++) {
      num_lines = doc->paragraphs[i].number_of_lines;
      if (num_lines > 0) {
        for (j = 0; j < num_lines; j++) {
          printf("%s\n", doc->paragraphs[i].lines[j]);
        }
        if (i < (doc->number_of_paragraphs - 1)) {
          printf("\n");
        }
      }
    }
  }
  return SUCCESS;
}
/*
Adds a paragraph after an at a given index. If it is the first paragraph to be
added, just add to index zero of the paragraphs array. If it adds it to the end,
it just adds it to the given index of the paragraph array. If it is to be added
somewhere in the middle, start at the number of paragraphs index of the
paragraph array and then assign the paragraph in front of it to the current
index. Then when the loop gets to the paragraph index, add the paragraph there.
  */
int add_paragraph_after(Document *doc, int paragraph_number) {
  int start, i;

  if (doc == NULL || doc->number_of_paragraphs >= MAX_PARAGRAPHS ||
      paragraph_number >= MAX_PARAGRAPHS ||
      doc->number_of_paragraphs < paragraph_number) {
    return FAILURE;

  } else if (paragraph_number == doc->number_of_paragraphs) {
    doc->paragraphs[doc->number_of_paragraphs].number_of_lines = 0;
    doc->number_of_paragraphs++;
    return SUCCESS;
  } else {

    for (i = doc->number_of_paragraphs - 1; i > paragraph_number - 1; i--) {
      doc->paragraphs[i + 1] = doc->paragraphs[i];
      if (i == paragraph_number) {
        doc->paragraphs[i].number_of_lines = 0;
        doc->number_of_paragraphs++;
        break;
      }
    }
    return SUCCESS;
  }
}
/*
Adds a line after a line. If the line is the first to be added to that
paragraph, copy to the lines paragraph index zero the new_line. If the line is
to be added at the end, copy the newline to that index. If it is to be added
somewhere in the middle of the paragraph, start at the end of the paragraph.
Copy to the string after the current string at that index. When the index equals
the target line, copy the newline to the curent index.
  */
int add_line_after(Document *doc, int paragraph_number, int line_number,
                   const char *new_line) {
  int i, pos;

  if (doc == NULL || paragraph_number > MAX_PARAGRAPHS ||
      doc->paragraphs[paragraph_number - 1].number_of_lines >=
          MAX_PARAGRAPH_LINES ||
      line_number >= MAX_PARAGRAPH_LINES || new_line == NULL ||
      doc->paragraphs[paragraph_number - 1].number_of_lines < line_number ||
      paragraph_number > doc->number_of_paragraphs) {
    return FAILURE;

  } else if (line_number ==
             doc->paragraphs[paragraph_number - 1].number_of_lines) {
    strcpy(doc->paragraphs[paragraph_number - 1].lines[line_number], new_line);
    doc->paragraphs[paragraph_number - 1].number_of_lines++;
    return SUCCESS;

  } else {
    pos = doc->paragraphs[paragraph_number - 1].number_of_lines - 1;
    for (i = pos; i > line_number - 1; i--) {
      strcpy(doc->paragraphs[paragraph_number - 1].lines[i + 1],
             doc->paragraphs[paragraph_number - 1].lines[i]);
      if (i == line_number) {
        strcpy(doc->paragraphs[paragraph_number - 1].lines[i], new_line);
        doc->paragraphs[paragraph_number - 1].number_of_lines++;
        break;
      }
    }
    return SUCCESS;
  }
}
/*
Set number_of_lines value to the number of lines of that paragraph number.
*/
int get_number_lines_paragraph(Document *doc, int paragraph_number,
                               int *number_of_lines) {
  if (doc == NULL || number_of_lines == NULL ||
      paragraph_number > MAX_PARAGRAPHS) {
    return FAILURE;
  } else {
    *number_of_lines = doc->paragraphs[paragraph_number - 1].number_of_lines;
    return SUCCESS;
  }
}
/*
Appends a paragraph. Adds a line to the end of the paragraph.
  */
int append_line(Document *doc, int paragraph_number, const char *new_line) {
  if (doc == NULL || paragraph_number > MAX_PARAGRAPHS ||
      doc->paragraphs[paragraph_number - 1].number_of_lines >=
          MAX_PARAGRAPH_LINES ||
      new_line == NULL) {
    return FAILURE;
  } else {
    add_line_after(doc, paragraph_number,
                   doc->paragraphs[paragraph_number - 1].number_of_lines,
                   new_line);

    return SUCCESS;
  }
}
/*
Removes a line from a paragraph. End index is set to the number of lines of that
paragraph. Start is set to the index of line_number. Coppies all of them over
until you reach the end.
  */
int remove_line(Document *doc, int paragraph_number, int line_number) {
  int end, start;
  if (doc == NULL || paragraph_number > MAX_PARAGRAPHS ||
      line_number > MAX_PARAGRAPH_LINES ||
      line_number > doc->paragraphs[paragraph_number - 1].number_of_lines) {
    return FAILURE;
  } else {
    end = doc->paragraphs[paragraph_number - 1].number_of_lines;
    for (start = line_number - 1; start < end; start++) {
      strcpy(doc->paragraphs[paragraph_number - 1].lines[start],
             doc->paragraphs[paragraph_number - 1].lines[start + 1]);
    }
    doc->paragraphs[paragraph_number - 1].number_of_lines--;
    return SUCCESS;
  }
}
/*
Loads a document by adding a paragraph to the end of the document. Then it goes
through that the data data_lines times and checks if the line is a blank line.
If its a blank line it adds another paragraph, otherwise it just adds a line to
the end.
  */
int load_document(Document *doc, char data[][MAX_STR_SIZE + 1],
                  int data_lines) {
  int i, paragraphs;

  if (doc == NULL || data == NULL || data_lines <= 0) {
    return FAILURE;
  } else {
    add_paragraph_after(doc, doc->number_of_paragraphs);

    for (i = 0; i < data_lines; i++) {
      paragraphs = doc->number_of_paragraphs;
      if (strcmp(data[i], "\0") == 0) {
        add_paragraph_after(doc, paragraphs);
      } else {

        add_line_after(doc, paragraphs,
                       doc->paragraphs[paragraphs - 1].number_of_lines,
                       data[i]);
      }
    }
    return SUCCESS;
  }
}
/*
Goes through all the paragraph and each paragraphs individual lines. Using the
strstr function, the substring that is to be found is located in the line. Then,
while that line is not a null byte, copy the target to the temporary holder
character array. It is then made blank. We then concatenate the found target and
the replacement, and then then concatenate again the two bigger strings to
replace the string. Then, the target_found string looks for another instance of
the target, making sure it does not include the one that was just found.
  */
int replace_text(Document *doc, const char *target, const char *replacement) {
  int i, j, tar_length, rep_len, num_paragraphs, curr_paragraph;
  char *target_found, holder[MAX_STR_SIZE + 1];

  if (doc == NULL || target == NULL || replacement == NULL) {
    return FAILURE;
  } else {
    tar_length = strlen(target);
    rep_len = strlen(replacement);
    num_paragraphs = doc->number_of_paragraphs;
    curr_paragraph = 0;
    while (curr_paragraph < num_paragraphs) {
      for (i = 0; i < doc->paragraphs[curr_paragraph].number_of_lines; i++) {
        target_found = strstr(doc->paragraphs[curr_paragraph].lines[i], target);

        while (target_found != NULL) {
          strcpy(holder, target_found);
          strcpy(target_found, "");
          strcat(target_found, replacement);
          strcat(target_found + rep_len, holder + tar_length);
          target_found = strstr(target_found + rep_len, target);
        }
      }
      curr_paragraph++;
    }
    return SUCCESS;
  }
}
/*
First, concatenate the opening and closing brackets to target, to make target
have the brackets. Then just call replace text function to replace the target
with the new target string with the brackets.
  */
int highlight_text(Document *doc, const char *target) {
  int num_paragraphs, curr_paragraph;
  char highlight_start[MAX_STR_SIZE + 1], highlight_end[MAX_STR_SIZE + 1],
      target_holder[MAX_STR_SIZE + 1];

  if (doc == NULL || target == NULL) {
    return FAILURE;
  } else {
    strcpy(target_holder, target);
    strcpy(highlight_start, HIGHLIGHT_START_STR);
    strcpy(highlight_end, HIGHLIGHT_END_STR);
    strcat(highlight_start, target_holder);
    strcat(highlight_start, highlight_end);
    replace_text(doc, target, highlight_start);
    return SUCCESS;
  }
}
/*
Replace the text with an empty string.
  */
int remove_text(Document *doc, const char *target) {
  if (doc == NULL || target == NULL) {
    return FAILURE;
  } else {
    replace_text(doc, target, "");
    return SUCCESS;
  }
}
/*  Loads a file into the document. First, open the file for reading. Returns
 * FAILURE if certain conditions are not met. Otherwise, this function adds the
 * file to the top of the document. So initally add to start of the document. If
 * the current line is empty, you add it as another paragraph. If there is no
 * space in between lines it gets appended. If add_paragraph_after fails return
 * FAILURE. Keeps reading in paragraphs until MAX_PARAGRAPHS is reached.
 */
int load_file(Document *doc, const char *filename) {
  FILE *file = fopen(filename, "r");
  char curr_line[MAX_LINE + 1];
  int paragraphs = 0, curr_len, counter = 0;

  if (doc == NULL || filename == NULL ||
      doc->number_of_paragraphs >= MAX_PARAGRAPHS || file == NULL) {
    fclose(file);
    return FAILURE;
  } else {

    add_paragraph_after(doc, 0);
    paragraphs++;

    while (fgets(curr_line, MAX_LINE + 1, file)) {
      if (doc->number_of_paragraphs > MAX_PARAGRAPHS) {
        return FAILURE;
      }

      if (is_empty(curr_line) == SUCCESS) {

        if (add_paragraph_after(doc, paragraphs++) == FAILURE) {
          return FAILURE;
        }

      } else {

        if (curr_line[strlen(curr_line) - 1] == '\n') {
          curr_line[strlen(curr_line) - 1] = '\0';
        }

        append_line(doc, paragraphs, curr_line);
      }
    }
    fclose(file);
    return SUCCESS;
  }
}

/*  Reads the document contents into a new file. Adds a newline character to the
 * line.  */
int save_document(Document *doc, const char *filename) {
  FILE *file = fopen(filename, "w");
  char curr_line[MAX_LINE + 1];
  int i, j, lines;

  if (doc == NULL || filename == NULL || file == NULL) {
    fclose(file);
    return FAILURE;
  }
  for (i = 0; i <= doc->number_of_paragraphs; i++) {
    lines = doc->paragraphs[i].number_of_lines;
    for (j = 0; j <= lines; j++) {
      fputs(doc->paragraphs[i].lines[j], file);
      if (j < lines) {
        fputs("\n", file);
      }
    }
    if (i + 1 < doc->number_of_paragraphs) {
      fputs("\n", file);
    }
  }
  fclose(file);
  return SUCCESS;
}
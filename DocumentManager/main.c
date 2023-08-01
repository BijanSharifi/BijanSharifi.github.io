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
      if(doc->number_of_paragraphs>MAX_PARAGRAPHS){
        return FAILURE;
      }
    
      

      if (is_empty(curr_line) == SUCCESS) {
        
        if(add_paragraph_after(doc, paragraphs++)==FAILURE){
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

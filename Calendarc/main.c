#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SUCCESS 0
#define FAILURE -1

typedef struct event {
  char *name;
  int start_time, duration_minutes;
  void *info;
  struct event *next;
} Event;

typedef struct calendar {
  char *name;
  Event **events;
  int days, total_events;
  int (*comp_func)(const void *ptr1, const void *ptr2);
  void (*free_info_func)(void *ptr);
} Calendar;


typedef struct task_info {
  double cost;
  char *prog_language;
} Task_info;


int init_calendar(const char *name, int days,
                  int (*comp_func) (const void *ptr1, const void *ptr2),
                  void (*free_info_func) (void *ptr), Calendar ** calendar);
int print_calendar(Calendar *calendar, FILE *output_stream, int print_all);
int add_event(Calendar *calendar, const char *name, int start_time,
              int duration_minutes, void *info, int day);
int find_event(Calendar *calendar, const char *name, Event **event);
int find_event_in_day(Calendar *calendar, const char *name, int day,
                      Event **event);
int remove_event(Calendar *calendar, const char *name);
void *get_event_info(Calendar *calendar, const char *name);
int clear_calendar(Calendar *calendar);
int clear_day(Calendar *calendar, int day);
int destroy_calendar(Calendar *calendar);


static int comp_minutes(const void *ptr1, const void *ptr2);
static int comp_duration_minutes(const void *ptr1, const void *ptr2);
static int comp_name(const void *ptr1, const void *ptr2);
static Task_info *create_task_info(double cost, const char *prog_language);
static void free_task_info(void *ptr);


static Task_info *create_task_info(double cost, const char *prog_language) {
   Task_info *task_info = malloc(sizeof(Task_info));

   if (task_info) {
      task_info->prog_language = malloc(strlen(prog_language) + 1);
      if (task_info->prog_language) {
         task_info->cost = cost;
         strcpy(task_info->prog_language, prog_language);
         return task_info;
      }
   }

   return NULL;
}

static void free_task_info(void *ptr) {
   Task_info *task_info = (Task_info *)ptr;

   free(task_info->prog_language);
  
   free(task_info);

}





static int comp_name(const void *ptr1, const void *ptr2) {
   return strcmp(((Event *)ptr1)->name, ((Event *)ptr2)->name);
}


static int comp_duration_minutes(const void *ptr1, const void *ptr2) {
  return ((Event *)ptr1)->duration_minutes - ((Event *)ptr2)->duration_minutes;
}

static int comp_minutes(const void *ptr1, const void *ptr2) {
   return ((Event *)ptr1)->duration_minutes - ((Event *)ptr2)->duration_minutes;
}

static int test1() {
   int days = 7;
   Calendar *calendar;

   if (init_calendar("Spr", days, comp_minutes, NULL, &calendar) == SUCCESS) {
      if (print_calendar(calendar, stdout, 1) == SUCCESS) {
         return destroy_calendar(calendar);
      }
   }
    
   return FAILURE;
}

static int test2(){
  Calendar *calendar;
  if(init_calendar("Test add to head", 5, comp_minutes, NULL, &calendar)==SUCCESS){
    if(add_event(calendar, "Event 1", 530, 60, NULL, 1)==SUCCESS){
      if(add_event(calendar, "Event 2", 420, 30, NULL, 1)==SUCCESS){
        if(add_event(calendar, "Event 3", 230, 60, NULL, 1)==SUCCESS){
          if(add_event(calendar, "Event 4", 830, 20, NULL, 1)==SUCCESS){
            if(add_event(calendar, "Event 5", 1230, 180, NULL, 2)==SUCCESS){
              if(add_event(calendar, "Event 6", 330, 120, NULL, 2)==SUCCESS){
                return print_calendar(calendar, stdout, 1);
              }
              
            }
            
          }
          
        }
      }
    }
  }
  return FAILURE;
}

static int test3(){
  Calendar *calendar;
  Event *event;
  if(init_calendar("Test find event", 5, comp_minutes, NULL, &calendar)==SUCCESS){
    if(add_event(calendar, "Event 1", 1400, 60, NULL, 1)==SUCCESS){
      /*  Tests edge case for adding same event */
      if(add_event(calendar, "Event 1", 0030, 60, NULL, 1)==FAILURE){
        if(find_event(calendar, "Event 1", &event)==SUCCESS){
        if(print_calendar(calendar, stdout, 1)==SUCCESS){
          return strcmp(event->name, "Event 1");
        }
        
      }
      }
      
    }
  }
  return FAILURE;
}

static int test4(){
  Calendar *calendar;
  Event *event;

  if(init_calendar("Test find event in day", 5, comp_minutes, NULL, &calendar)==SUCCESS){
    if(add_event(calendar, "Event 1", 30, 60, NULL, 1)==SUCCESS){
      if(add_event(calendar, "Event 2", 100, 120, NULL, 2)==SUCCESS){
        if(add_event(calendar, "Event2", 1200, 120, NULL, 2)==SUCCESS){
          if(find_event_in_day(calendar, "Event 2", 2, &event)==SUCCESS){
          if(print_calendar(calendar, stdout, 1)==SUCCESS){
            
            return strcmp(event->name, "Event 2");
          }
        }
        }
        
      }
    }
  }
  return FAILURE;
  
}

static int test5(){
  Calendar *calendar; 

  if(init_calendar("Test remove event", 5, comp_minutes, NULL, &calendar)==SUCCESS){
    if(add_event(calendar, "Event 1", 0001, 60, NULL, 1)==SUCCESS){
      /* Making sure event time is valid*/
      if(add_event(calendar, "Event 2", 2401, 60, NULL, 2)==FAILURE){
      if(add_event(calendar, "Event 2", 1200, 120, NULL, 1)==SUCCESS){
        /* Makes sure remove_event returns FAILURE if the event is not presant  */
        if(remove_event(calendar, "Event 3")==FAILURE){
          if(calendar->total_events!=2){
            return FAILURE;
          }else{
            if(add_event(calendar, "Event 3", 1450, 30, NULL, 2)==SUCCESS){
              if(print_calendar(calendar, stdout, 1)==SUCCESS){
                return remove_event(calendar, "Event 1");
              }
            }
          }
          
        }
      }
        
      }
    }
  }
  return FAILURE;
  
}

static int test6(){
  Calendar *calendar;
  Task_info *info1, *info2, *info3;
  

  if(init_calendar("Test add with task and get event info", 5, comp_minutes, free_task_info, &calendar)==SUCCESS){
    info1=create_task_info(35, "Java");
    if(add_event(calendar, "Event with info 1", 230, 30, info1, 1)==SUCCESS){
      info2=create_task_info(100, "Hello");
      if(add_event(calendar, "Event with info 2", 330, 60, info2, 1)==SUCCESS){
         if(print_calendar(calendar, stdout, 1)==SUCCESS){
           info3=get_event_info(calendar, "Event with info 1");
           printf("Info: %.2f %s\n", info3->cost, info3->prog_language);
        return SUCCESS;
      }
      }
     
    }
  }
  return FAILURE;
  
}

static int test7(){
  Calendar *calendar;
  Task_info *info, *info1, *info2;

  if(init_calendar("Test remove with task and get event info", 5, comp_minutes, free_task_info, &calendar)==SUCCESS){
    info=create_task_info(35, "Java");
    if(add_event(calendar, "Event with info 1", 230, 30, info, 1)==SUCCESS){
      info=create_task_info(750, "Tom Brady");
      if(add_event(calendar, "Event with info 2", 1650, 240, info, 1)==SUCCESS){
        info=create_task_info(1000, "Jameis Winston");
        if(add_event(calendar, "Event with info 3", 2100, 60, info, 2)==SUCCESS){
          if(remove_event(calendar, "Event with info 1")==SUCCESS){
            if(print_calendar(calendar, stdout, 1)==SUCCESS){
              if(get_event_info(calendar, "Event with info 2")!=NULL &&
              get_event_info(calendar, "Event with info 3")!=NULL){
                info1=get_event_info(calendar, "Event with info 2");
                info2=get_event_info(calendar, "Event with info 3");
                printf("Event info: %.2f %s\n", info1->cost, info1->prog_language);
                printf("Event info: %.2f %s\n", info2->cost, info2->prog_language);
                if(get_event_info(calendar, "Event with info 1")==NULL){
                  return SUCCESS;
                }
                  
                
                
                
              }
            }
          }
        }
      }
    }
    
    
  }
  return FAILURE;
}

static int test8(){
  Calendar *calendar;
  Task_info *task1, *task2, *task3;

  

  
    if(init_calendar("Test clear day", 10, comp_minutes, free_task_info, &calendar)==SUCCESS){
  task1=create_task_info(64, "Lil Uzi VErT");
  task2=create_task_info(120, "Kanye West");
  task3=create_task_info(200, "Travis Scott");
    if(add_event(calendar, "Event 1", 01, 120, task1, 1)==SUCCESS){
      if(add_event(calendar, "Event 2", 430, 60, NULL, 2)==SUCCESS){
        if(add_event(calendar, "Event 11", 2300, 30, NULL, 11)==FAILURE){
          if(add_event(calendar, "Event 3", 45, 45, NULL, 3)==SUCCESS){
            if(add_event(calendar, "Event 7", 930, 15, NULL, 7)==SUCCESS){
              if(add_event(calendar, "Event 2 1", 700, 70, task2, 1)==SUCCESS){
                if(add_event(calendar, "Event 3 1", 1045, 60, task3, 1)==SUCCESS){
                  
                    if(clear_day(calendar, 1)==SUCCESS){
                      if(print_calendar(calendar, stdout, 1)==SUCCESS){
                        return SUCCESS;
                        
                        
                      }
                    }
                    
                  
                }
              }
            }
          }
        }
      }
    }
    
    
  }

  return FAILURE;
  


  
}

static int test9(){
  Calendar *calendar;
  Task_info *task1, *task2, *task3;

  

  
    if(init_calendar("Test calendar", 10, comp_minutes, free_task_info, &calendar)==SUCCESS){
  task1=create_task_info(64, "Lil Uzi VErT");
  task2=create_task_info(120, "Kanye West");
  task3=create_task_info(200, "Travis Scott");
    if(add_event(calendar, "Event 1", 01, 120, task1, 1)==SUCCESS){
      if(add_event(calendar, "Event 2", 430, 60, NULL, 2)==SUCCESS){
        if(add_event(calendar, "Event 11", 2300, 30, NULL, 11)==FAILURE){
          if(add_event(calendar, "Event 3", 45, 45, NULL, 3)==SUCCESS){
            if(add_event(calendar, "Event 7", 930, 15, NULL, 7)==SUCCESS){
              if(add_event(calendar, "Event 2 1", 700, 70, task2, 1)==SUCCESS){
                if(add_event(calendar, "Event 3 1", 1045, 60, task3, 1)==SUCCESS){
                  if(clear_calendar(calendar)==SUCCESS){
                    if(print_calendar(calendar, stdout, 1)==SUCCESS){
                      return SUCCESS;
                    }
                  }
                    
                  
                }
              }
            }
          }
        }
      }
    }
    
    
  }

  return FAILURE;

  
}

static int test10(){
Calendar *calendar;
Task_info *task1, *task2, *task3;


if(init_calendar("Test destroy", 15, comp_minutes, free_task_info, &calendar)==SUCCESS){
  task1=create_task_info(64, "Lil Uzi VErT");
  task2=create_task_info(120, "Kanye West");
  task3=create_task_info(200, "Travis Scott");
    if(add_event(calendar, "Event 1", 01, 120, task1, 1)==SUCCESS){
      if(add_event(calendar, "Event 2", 430, 60, NULL, 2)==SUCCESS){
        if(add_event(calendar, "Event 11", 2300, 30, NULL, 11)==FAILURE){
          if(add_event(calendar, "Event 3", 45, 45, NULL, 3)==SUCCESS){
            if(add_event(calendar, "Event 7", 930, 15, NULL, 7)==SUCCESS){
              if(add_event(calendar, "Event 2 1", 700, 70, task2, 1)==SUCCESS){
                if(add_event(calendar, "Event 3 1", 1045, 60, task3, 1)==SUCCESS){
                  if(destroy_calendar(calendar)==SUCCESS){
                    calendar=NULL;
                    if(print_calendar(calendar, stdout, 1)==FAILURE){
                      return SUCCESS;
                    }
                    } 
                  
                    
                  
                }
              }
            }
          }
        }
      }
    }
  
}
  return FAILURE;
  
}


int main() {
  
  int result = SUCCESS;
  printf("Test 1\n");
    if (test1() == FAILURE){
      result = FAILURE;
      } 
  printf("\nTest 2\n");
    if(test2()==FAILURE){
        result=FAILURE;
      }
  printf("\nTest 3\n");
  if(test3()!=0){
    result= FAILURE;
  }
  printf("\nTest 4\n");
  if(test4()!=0){
    result= FAILURE;
  }
  printf("\nTest 5\n");
  if(test5()==FAILURE){
    result=FAILURE;
  }
  printf("\nTest 6\n");
  if(test6()==FAILURE){
    result =FAILURE;
  }
  printf("\nTest 7\n");
  if(test7()==FAILURE){
    result=FAILURE;
  }
  printf("\nTest 8\n");
  if(test8()==FAILURE){
    result=FAILURE;
  }
  printf("\nTest 9\n");
  if(test9()==FAILURE){
    result=FAILURE;
  }
  printf("\nTest 10\n");
  if(test10()==FAILURE){
    result=FAILURE;
  }
  
    

  if(result==FAILURE){
    printf("This test failed");
  }else{
    printf("SUCCESS");
  }
  return 0;

  
}




/* Function that initializes a calendar given the parameters. If any memory
 * allocation fails for the calendar or any of the fields, the function returns
 * failure. Otherwise, assign all the fields to the calendar.
 */
int init_calendar(const char *name, int days,
                  int (*comp_func)(const void *ptr1, const void *ptr2),
                  void (*free_info_func)(void *ptr), Calendar **calendar) {
  if (*calendar == NULL || name == NULL || days < 1) {
    return FAILURE;
  } else {
    *calendar = malloc(sizeof(Calendar));
    (*calendar)->name = malloc(strlen(name) + 1);
    

    (*calendar)->events = calloc(days, sizeof(Event));
    if (calendar == NULL || (*calendar)->name == NULL ||
        (*calendar)->events == NULL) {
      return FAILURE;
    } else {
      /* *calendar is used to assign to fields because the calendar being passed
       * in a double pointer.  */
      strcpy((*calendar)->name, name);
      (*calendar)->days = days;
      (*calendar)->total_events = 0;
      (*calendar)->comp_func = comp_func;
      (*calendar)->free_info_func = free_info_func;
      return SUCCESS;
    }
  }
}

int print_calendar(Calendar *calendar, FILE *output_stream, int print_all) {
  int i;
  Event *curr;

  if (calendar == NULL || output_stream == NULL) {
    return FAILURE;
  } else {
    if (print_all == 1) {
      fprintf(output_stream, "Calendar's Name: \"%s\"\n", calendar->name);
      fprintf(output_stream, "Days: %d\n", calendar->days);
      fprintf(output_stream, "Total Events: %d\n", calendar->total_events);
    }
    fprintf(output_stream, "\n**** Events ****\n");
    /* Nested loops that go through all the days, and also all the events in a
     * day to print out every day and every event in that day */
    if (calendar->total_events > 0) {
      for (i = 0; i < calendar->days; i++) {
        fprintf(output_stream, "Day %d\n", i + 1);
        for (curr = calendar->events[i]; curr != NULL; curr = curr->next) {
          fprintf(output_stream, "Event's Name: \"%s\"", curr->name);
          fprintf(output_stream, ", Start_time: %d", curr->start_time);
          fprintf(output_stream, ", Duration: %d\n", curr->duration_minutes);
        }
      }
    }

    return SUCCESS;
  }
}
/* Adds an event to a day and added based on calendars comp func    */
int add_event(Calendar *calendar, const char *name, int start_time,
              int duration_minutes, void *info, int day) {
  int i;
  Event *event, *prev, *curr, *dum;

  if (calendar == NULL || name == NULL || start_time < 0 || start_time > 2400 ||
      duration_minutes <= 0 || day < 1 || day > calendar->days) {
    return FAILURE;
  }
  /* If the event already exists, return FAILURE */
  if (find_event(calendar, name, &dum) == SUCCESS) {
    return FAILURE;
  }
  /* Allocate space for the event and the fields, returning FAILURE if any of
   * the allocation goes wrong */
  event = malloc(sizeof(Event));
  if (event != NULL) {
    event->name = malloc(strlen(name) + 1);
    if (event->name != NULL) {
      strcpy(event->name, name);
      event->start_time = start_time;
      event->duration_minutes = duration_minutes;
      event->info = info;
      /* Sets the trailing event to NULL    */
      prev = NULL;
      for (curr = calendar->events[day - 1];
           curr != NULL && calendar->comp_func(curr, event) <= 0;
           curr = curr->next) {
        prev = curr;
      }
      /* Curr needs to be added to the head of the linked list based on the comp
       * func*/
      if (prev == NULL) {
        calendar->events[day - 1] = event;
        event->next = curr;
        /* Adds the new event in between prev and curr, and setting there next
         * fields accordingly */
      } else {

        event->next = curr;
        prev->next = event;
      }
      calendar->total_events++;
      return SUCCESS;
    }
  }

  return FAILURE;
}

/* Finds an event in the calendar based on its name*/
int find_event(Calendar *calendar, const char *name, Event **event) {
  int i;
  Event *curr;

  if (calendar == NULL || name == NULL) {
    return FAILURE;
  }
  /* Similar to print, as we need two loops to go through all the days and to go
   * through all the events in the day. If the names are found t be the same,
   * set the event field to curr and it can now be used outside the function */
  for (i = 0; i < calendar->days; i++) {
    for (curr = calendar->events[i]; curr != NULL; curr = curr->next) {
      if (strcmp(curr->name, name) == 0) {
        if (event != NULL) {
          *event = curr;
        }

        return SUCCESS;
      }
    }
  }
  return FAILURE;
}
/* Finds an event in a specific day */
int find_event_in_day(Calendar *calendar, const char *name, int day,
                      Event **event) {
  int i;
  Event *curr;

  if (calendar == NULL || name == NULL || day < 1 || day > calendar->days) {
    return FAILURE;
  }
  /* Sets curr to the head of the linked list of the specified day. Then just go
   * through the linked list until the name is found.*/
  for (curr = calendar->events[day - 1]; curr != NULL; curr = curr->next) {
    if (strcmp(curr->name, name) == 0) {
      if (event != NULL) {
        *event = curr;
      }
      return SUCCESS;
    }
  }
  return FAILURE;
}

int remove_event(Calendar *calendar, const char *name) {
  Event *temp_event, *curr, *prev;
  int i = 0, day, free_func;

  if (calendar == NULL || name == NULL ||
      find_event(calendar, name, &temp_event) == FAILURE) {
    return FAILURE;
  }
/* Checks if calendar has a free info func */
  if (calendar->free_info_func) {
    free_func = 1;
  }
/*  Finds the day that the event is in, and keeps that index in day    */
  for (i = 0; i < calendar->days; i++) {
    if (find_event_in_day(calendar, name, i + 1, &temp_event) == SUCCESS) {
      day = i;
      break;
    }
  }
  /*  If an event is never found temp_event is null and this function returns FAILURE   */
  if (temp_event == NULL) {
    return FAILURE;

  } else {
    /*  Set curr equal to the head of the linked list for that day.    */
    curr = calendar->events[day];
    prev = NULL;
    while (curr != NULL && strcmp(curr->name, name) != 0) {
      prev = curr;
      curr = curr->next;
    }
    /* If the day is empty, there is nothing to do so return FAILURE */
    if (curr == NULL) {
      return FAILURE;
    }
    /*  If prev is NULL that means the head of the list needs to be removed, so set the head of the list to curr->next. Then free curr and all the fields.    */
    if (prev == NULL) {
      calendar->events[day] = curr->next;

      free(curr->name);
      if (free_func == 1 && curr->info) {
        calendar->free_info_func(curr->info);
      }
      free(curr);
/*   Prev->next is set to curr->next and free everything in curr   */
    } else {
      prev->next = curr->next;
      free(curr->name);
      if (free_func == 1 && curr->info) {
        calendar->free_info_func(curr->info);
      }
      free(curr);
    }
  }
  calendar->total_events--;
  return SUCCESS;
}
/*  Returns a pointer to the info field of an event, using find_event  */
void *get_event_info(Calendar *calendar, const char *name) {
  Event *event;

  if (find_event(calendar, name, &event) == FAILURE) {
    return NULL;
  }

  return event->info;
}

int clear_day(Calendar *calendar, int day) {
  int i;
  Event *event, *temp_ptr;

  if (calendar == NULL || day < 1 || day > calendar->days) {
    return FAILURE;
  }
  
  if (calendar->total_events > 0) {
    /*  Set event pointer to head of list.    */
    event = calendar->events[day - 1];
    /* Set a temporary pointer to the event pointer, and move event to the next. free the temporary pointer and its fields. */
    while (event != NULL) {
      temp_ptr = event;
      event = event->next;
      free(temp_ptr->name);
      /* Checks if the event field is not NULL and that the calender has a free info func */
      if (calendar->free_info_func && temp_ptr->info) {
        calendar->free_info_func(temp_ptr->info);
        
        
      }
      free(temp_ptr);
      calendar->total_events--;
    }
  }
  calendar->events[day - 1] = NULL;
  return SUCCESS;
}
/*  Calls clear day on each day*/
int clear_calendar(Calendar *calendar) {
  int i;

  if (calendar == NULL) {
    return FAILURE;
  } else {
    for (i = 0; i < calendar->days; i++) {
      clear_day(calendar, i + 1);
    }
    calendar->total_events = 0;
    return SUCCESS;
  }
}
/* Calls clear calendar to clear all the events of all the days, as well as freeing the name, event pointer and free calendar */
int destroy_calendar(Calendar *calendar) {

  if (calendar == NULL) {
    return FAILURE;
  } else {
    free(calendar->name);
    clear_calendar(calendar);

    free(calendar->events);
    free(calendar);
    return SUCCESS;
  }
}

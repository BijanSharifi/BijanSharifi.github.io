#include <math.h>
#include <stdio.h>
#define MAX_ARR_SIZE 50

void add_assingment(int number[], int score[], int weight[], int day[], int num_of_assign);
void compute(int number[], int score[], int weight[], int day[], int penaltys, int dropped, int num_of_assign, char stat);
void initialize(int arr[]);
void drop_assignment(int number[], int score[], int weight[], int day[], int num_of_assign, int dropped, int penaltys, char stats);

/*
Main function in which 4 arrays of size 50 get initialized. There is an array for the assignment number, the score, the weight, and the number of days late. The number of points off per day late, the number of dropped assignments, the number of assignments, and the option to see stats are scanned in. Also, if the weights don't add up to 100 the program ends.
*/  
int main() {
  int penalty, drop, num, num_arr[MAX_ARR_SIZE], score_arr[MAX_ARR_SIZE], weight_arr[MAX_ARR_SIZE], day_arr[MAX_ARR_SIZE], penatly, i, counter;
  char stats;

  initialize(num_arr);
  initialize(score_arr);
  initialize(weight_arr);
  initialize(day_arr);
  scanf("%d %d %c", &penalty, &drop, &stats);
  scanf("%d", &num);
  add_assingment(num_arr, score_arr, weight_arr, day_arr, num);

  counter = 0;
  for (i = 0; i < num; i++) {
    counter += weight_arr[i];
  }
  if (counter != 100) {
    printf("ERROR: Invalid values provided");
    return 0;
  } else {
    compute(num_arr, score_arr, weight_arr, day_arr, penalty, drop, num, stats);
  }
  return 0;
}
/*
Function which takes in the 4 arrays and the number of assignments. Use a loop to keep scanning in assignments and adding them all to the same index of there respective arrays. After the loop ends, all the assignments are in the right order.
*/
void add_assingment(int number[], int scores[], int weights[], int day[],int num_of_assign) {
  int assign_num, score, weight, days_late, i;
  for (i = 0; i < num_of_assign; i++) {
    scanf("%d, %d, %d, %d", &assign_num, &score, &weight, &days_late);
    number[assign_num - 1] = assign_num;
    scores[assign_num - 1] = score;
    weights[assign_num - 1] = weight;
    day[assign_num - 1] = days_late;
  }
}
/*
Funtion which computes the scores if the number of assignments dropped is 0. New array is made and initialized, which holds the scores after the number of days late is calculated. If the new score is below 0 with the number of days late taken into account, the score is just set to zero. Then the numeric score is calculated by multiplying the weight and score and them adding these up together. After, everything is printed and if the user wanted the stats the mean and standard deviation are computed.
*/
void compute(int number[], int score[], int weight[], int day[], int penaltys, int dropped, int num_of_assign, char stats) {
  int i, j, k, a, b, c, days_late, total_penalty, weight_value, score_value, score_after_late[MAX_ARR_SIZE];
  double score_counter, actual_score, mean_bef_div, mean, standard_dev[MAX_ARR_SIZE], squared_differences, variance, deviation, value[MAX_ARR_SIZE];
  

  score_counter = 0;
  actual_score = 0;
  weight_value = 0;
  score_value = 0;

  initialize(score_after_late);
  if (dropped == 0) {

    for (k = 0; k < num_of_assign; k++) {
      if (day[k] != 0) {
        days_late = day[k];
        total_penalty = days_late * penaltys;
        if (score[k] - total_penalty <= 0) {
          score_after_late[k] = 0;
        } else {
          score_after_late[k] = score[k] - total_penalty;
        }

      } else {
        score_after_late[k] = score[k];
      }
    }

    for (i = 0; i < num_of_assign; i++) {
      weight_value = weight[i];
      score_value = score_after_late[i];
      score_counter += weight_value * score_value;
    }
    actual_score = (score_counter) / 100;

    printf("Numeric Score: %5.4f\n", actual_score);
    printf("Points Penalty Per Day Late: %d\n", penaltys);
    printf("Number of Assignments Dropped: %d\n", dropped);
    printf("Values Provided:\nAssignment, Score, Weight, Days Late\n");
    for (j = 0; j < num_of_assign; j++) {
      printf("%d, %d, %d, %d\n", number[j], score[j], weight[j], day[j]);
    }

    if (stats == 'y' || stats == 'Y') {
      mean_bef_div = 0;
      mean = 0;
      for (a = 0; a < num_of_assign; a++) {
        mean_bef_div += score_after_late[a];
      }
      mean = mean_bef_div / num_of_assign;
      for (b = 0; b < num_of_assign; b++) {
        standard_dev[b] = mean - score_after_late[b];
        standard_dev[b] = standard_dev[b] * standard_dev[b];
      }
      squared_differences = 0;
      for (c = 0; c < num_of_assign; c++) {
        squared_differences += standard_dev[c];
      }
      variance = squared_differences / num_of_assign;
      deviation = sqrt(variance);

      printf("Mean: %5.4f, Standard Deviation: %5.4f\n", mean, deviation);
    }

  } else {
    drop_assignment(number, score, weight, day, num_of_assign, dropped, penaltys, stats);
  }
}
/*
Initializes arrays
*/
void initialize(int arr[]) {
  int i;
  for (i = 0; i < MAX_ARR_SIZE; i++) {
    arr[i] = -1;
  }
}

  /* 
  If an assignment(s) need to be dropped, this function runs. Start off by making another array and storing the values of each assignment. We then make another new array, which has the new weights, but to start they are just coppied over. Then go through the values array, and remove the lowest valued assignments, until the dropping is the same as dropped. The assignments that get dropped, get there new weights set to 0. Then the days late are taken into account. Then the rest of the function is the same as the compute function.
  */
void drop_assignment(int number[], int score[], int weight[], int day[], int num_of_assign, int dropped, int penaltys, char stats) {
  int i, j, k, dropping, position, lowest, new_weights[MAX_ARR_SIZE], score_after_days[MAX_ARR_SIZE];
  double standard_dev[MAX_ARR_SIZE], value[MAX_ARR_SIZE], squared_differences, variance, deviation, actual_score, total_value, total_weight, mean_bef_div, mean;

  for (i = 0; i < num_of_assign; i++) {
    value[i] = (score[i] * weight[i]) / 100;
  }
  initialize(new_weights);
  for (k = 0; k < num_of_assign; k++) {
    new_weights[k] = weight[k];
  }
  dropping = 0;
  while (dropping < dropped) {
    position = 0;
    lowest = 10000;
    for (j = 0; j < num_of_assign; j++) {
      if (value[j] == -1) {
        continue;
      } else {
        if (lowest > value[j]) {
          lowest = value[j];
          position = j;
        }
      }
    }
    dropping++;
    value[position] = -1;
    new_weights[position] = 0;
  }
  initialize(score_after_days);
  for (k = 0; k < num_of_assign; k++) {
    if (day[k] == 0) {
      score_after_days[k] = score[k];
    } else {
      if (score[k] - (day[k] * penaltys) <= 0) {
        score_after_days[k] = 0;

      } else {
        score_after_days[k] = score[k] - (day[k] * penaltys);
      }
    }
  }
  total_value = 0;
  for (i = 0; i < num_of_assign; i++) {
    total_value += (new_weights[i]) * (score_after_days[i]);
  }
  total_weight = 0;
  for (i = 0; i < num_of_assign; i++) {
    total_weight += new_weights[i];
  }
  actual_score = total_value / total_weight;

  printf("Numeric Score: %5.4f\n", actual_score);
  printf("Points Penalty Per Day Late: %d\n", penaltys);
  printf("Number of Assignments Dropped: %d\n", dropped);
  printf("Values Provided:\nAssignment, Score, Weight, Days Late\n");
  for (j = 0; j < num_of_assign; j++) {
    printf("%d, %d, %d, %d\n", number[j], score[j], weight[j], day[j]);
  }
  if (stats == 'y' || stats == 'Y') {
    mean_bef_div = 0;
    mean = 0;
    for (i = 0; i < num_of_assign; i++) {
      mean_bef_div += score_after_days[i];
    }
    mean = mean_bef_div / num_of_assign;
    for (i = 0; i < num_of_assign; i++) {
      standard_dev[i] = mean - score_after_days[i];
      standard_dev[i] = standard_dev[i] * standard_dev[i];
    }
    squared_differences = 0;
    for (i = 0; i < num_of_assign; i++) {
      squared_differences += standard_dev[i];
    }
    variance = squared_differences / num_of_assign;
    deviation = sqrt(variance);

    printf("Mean: %5.4f, Standard Deviation: %5.4f\n", mean, deviation);
  }
}

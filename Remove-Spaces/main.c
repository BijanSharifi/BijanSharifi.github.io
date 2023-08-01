#include <stdio.h>
#include <string.h>

#define MAX_STR_LEN 80
#define SUCCESS 0
#define FAILURE -1



int remove_spaces(const char *source, char *result, int *num_spaces_removed) {  
  int i, j=0, spaces_removed=0, length;
  char temp;
  
  if(source==NULL){
    return FAILURE;
  }else{
      if(source[0]=='\0' || strlen(source)==0){
    return FAILURE;
    
  }else{
    length=strlen(source);   
    for(i=0; i<length; i++){
      temp=source[i];
      if(temp!=' '){
        result[j++]=source[i];
      }else{
        if(i+1<length && i-1>=0){
          if(source[i+1]!=' ' && source[i-1] !=' '){
            result[j++]=source[i];
          }else{
            spaces_removed++;
            
            
          }
        }else{
           spaces_removed++;
          
        }
       
        
      }
      
    }
    for(i=j; i<length; i++){
      result[i]='\0';
      
    }
    if(num_spaces_removed!=NULL){
       *num_spaces_removed=spaces_removed;
    }
   
    
    
    return SUCCESS;
   
  }
  }

}



int center(const char *source, int width, char *result) {
  int space=(width-strlen(source))/2, i, counter_index=0, result_length, length=strlen(source);

  if(source[0]=='\0' || length==0 || width<length){
    return FAILURE;
  }else{
    for(i=0; i<space; i++){
      result[counter_index]=' ';
      counter_index++;
    }
    for(i=0; i<length; i++){
      result[counter_index]=source[i];
      counter_index++;
      
    }
  for(i=0; i<space; i++){
    result[counter_index]=' ';
    counter_index++;
    
  }
  result_length=strlen(result);
  for(i=counter_index; i<result_length; i++){
    result[i]='\0';
    }
  
  
  
  return SUCCESS;
  }

}





int main() {
  char result[MAX_STR_LEN + 1];
  int spaces_removed, test_result;
  
  
  printf("Test 1");
   center("terps", 7, result);
   test_assert(strcmp(result, " terps ") == 0, "center", 1);

  printf("Test 2");
   remove_spaces("   maryland    ", result, &spaces_removed);
   test_result = strcmp(result, "maryland") == 0 && spaces_removed == 7;
   test_assert(test_result, "remove_spaces", 1);


  printf("Test 3");
  remove_spaces("   city    ", result, &spaces_removed);
   test_result = strcmp(result, "city") == 0 && spaces_removed == 7;
   test_assert(test_result, "remove_spaces", 2);

  



  printf("Test 4");
   center("cats", 5, result);
   test_assert(strcmp(result, "cats") == 0, "center", 1);


  



  printf("Test 5");
   center("dogs", 7, result);
   test_assert(strcmp(result, " dogs ") == 0, "center", 1);

 

  
  
   return 0;
}

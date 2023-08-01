#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_ALBUM_SIZE 8

typedef struct photo {
  int id;
  char *description; /* dynamically-allocated string */
} Photo;

typedef struct album {
  int size;
  Photo *all_photos[MAX_ALBUM_SIZE];
} Album;

Photo *create_photo(int id, const char *description);
void print_photo(Photo *photo);
void destroy_photo(Photo *photo);
void initialize_album(Album *album);
void print_album(const Album *album);
void destroy_album(Album *album);
void add_photo_to_album(Album *album, int id, const char *description);

int main() {
   Album album, second_album;
   
  // start_memory_check(); /* Start memory check */

   initialize_album(&album);
   add_photo_to_album(&album, -1, "Computer"); 
   add_photo_to_album(&album, 40, "Car"); 
   add_photo_to_album(&album, 8, "TV"); 
   print_album(&album);
   destroy_album(&album);

   initialize_album(&second_album);
   add_photo_to_album(&second_album, 100, "Shirt"); 
   add_photo_to_album(&second_album, 200, "Pants"); 
   add_photo_to_album(&second_album, 300, "Ties"); 
   print_album(&second_album);
   destroy_album(&second_album);



  // stop_memory_check(); /* End memory check */

   return EXIT_SUCCESS;
}


Photo *create_photo(int id, const char *description) {

  Photo *photo;
  photo=malloc(sizeof(Photo));
  if(photo==NULL){
    return NULL;
  }else if(description!=NULL){
    photo->description=malloc((strlen(description))+1);
    if(photo->description!=NULL){
      strcpy(photo->description, description);
    }
  }else{
    photo->description=NULL;
  }
  photo->id=id;
  return photo;
}

void print_photo(Photo *photo) {
  

  if(photo!=NULL){
    if(photo->description==NULL){
      printf("Photo Id: %d, Description: None\n", photo->id);
    }else{
      printf("Photo Id: %d, Description: %s\n", photo->id, photo->description);
      
    }
  }
  }

void destroy_photo(Photo *photo) {
  

  if(photo!=NULL){
    free(photo->description);
    photo->description=NULL;
    free(photo);
    photo=NULL;
  }
}

void initialize_album(Album *album) {
 
  

  if(album!=NULL){
    album->size=0;
  }
}

void print_album(const Album *album) {
  int i;



  if(album!=NULL){
    if(album->size==0){
      printf("Album has no photos.\n");
    }else{
       for (i = 0; i < album->size; i++) {
      print_photo(album->all_photos[i]);
    }
    }
  }
}
void destroy_album(Album *album){
  int i;
  
  

  if(album!=NULL){
     for(i=0; i<album->size; i++){
      destroy_photo(album->all_photos[i]);
    }
    album->size=0;
    
  }
}

void add_photo_to_album(Album *album, int id, const char *description){
  
  Photo *photo;

  if(album!=NULL){
    if(album->size<MAX_ALBUM_SIZE){
      photo=create_photo(id, description);
      if(photo!=NULL){
        album->all_photos[album->size]=photo;
        album->size++;
      }
    }
  }
  
  

  
}



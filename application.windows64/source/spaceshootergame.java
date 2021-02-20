import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class spaceshootergame extends PApplet {

int index2;

int spawnType = 0;

PFont f;

float rectWidth = 200;

boolean clicked = false;

int health = 100;

final int MAX_HEALTH = 100;

int score = 1;

int clicked_at_x;

int clicked_at_y;

int ch = 100;

int ph = 100;

int bs;

int zs;

int startX;

int startY;

int xValue1;

int yValue2;

PImage frozenrock;

PImage healthbar;

PImage enemyship;

PImage fire;

PImage img2;

PImage img;

int index;

int xValue = mouseX;

int yValue = mouseY;

boolean was_clicked = false;

boolean hit = false;

boolean endgame = false;

boolean started = false;

Rain r1;

Bullet bullet;

Star r2;

int numStars = 1;

Star[] stars = new Star[numStars];

int numDrops = 5;

Rain[] drops = new Rain[numDrops]; // Declare and create the array

public void setup() {
 
  frozenrock = loadImage("frozenrock.png");
  
  healthbar = loadImage("healthbar.png");
  
  enemyship = loadImage("enemyship.png");
  
  fire = loadImage("fire.png");
  
  img = loadImage("spaceshipmain.png");
  
  img2 = loadImage("spacebackground.png");
  
  img.resize(50,50);
  
  img2.resize(600, 600);
  
  healthbar.resize(400,180 );
  
  f = createFont("Stencil",16,true);
  
  
  
  xValue = mouseX;
  
  yValue = height - 100;
  
  xValue1 = 0;
  
  yValue2 = 0;

  
  
  noStroke();
  
  //Loop through array to create each object
  for (int i = 0; i < drops.length; i++) {

    drops[i] = new Rain(); // Create each object
    
    r1 = new Rain();
    
  }
  
  
  for (int i = 0; i < stars.length; i++) {

    stars[i] = new Star(); // Create each object
    
    r2 = new Star();
  }
  
 bullet = new Bullet();
 
 zs = -800;
 
 index = 0;
 
 bs = -800;
 
}

public void draw(){
  background(img2);
  
  if (started==false){
    
    displayStart();

    if (clicked == true){
      
     clicked = false;
     
     started = true; 
    }
  }
  
  if (started == true){
  
  removeHealth();
  
  if (health <= 0){
  
    displayEndgame();
    
    endgame = true;
    
    if (key == 'z'){
      
      clicked = true;
      
      started = false;
      
      endgame = false;
      
      score = 0;
      
      health = 100;
      
      startX = xValue1;
      
      startY = yValue2;
      
    }
  }
  
  if (endgame != true){
  
  textFont(f,36);

  text("SCORE: "+ (score - 1), width/2+100, 50);

  noCursor();
 
  image(img, mouseX, yValue);
  
  for (int i = 0; i < drops.length; i++) {
    
    drops[i].fall();
    
  }
  
  for (int i = 0; i < stars.length; i++) {
    
    stars[i].fall();
    
    float[] arr = stars[i].getCoords();
    
  }
  
 for (int i = 0; i < drops.length; i++){
   
   if (drops[i].check1(mouseX, yValue)){

     health--; 
     
    } 
  }
  
 fill(148, 10, 10);
 
 if (was_clicked) {
    
    image(fire, clicked_at_x+20, clicked_at_y);
    
   }
  
   clicked_at_y -= 20;
   
   int[] Ms = new int[6];
   
   Ms = new int[]{50,200, 100, 150, 300, 250,543 ,500, 345, 122, 540, 450, 245};
 
   boolean check = check(index, Ms,  clicked_at_y, clicked_at_x, zs);
      
   image(enemyship, Ms[index],zs);
      
   filter(DILATE);
        
   zs += 2;
  
   check = check(index, Ms,  clicked_at_y, clicked_at_x, zs);
   
   image(frozenrock, Ms[index2], bs);
   
   bs += 2;
   
   if (bs > height + 100){
    
    bs = -800; 
    
    index2 = (int)random(0,Ms.length);;
   }

  if (check == true){
    
   score++;
    
   int xd = clicked_at_x;

   zs = -600;
   
   index = (int)random(0,Ms.length);
   
   spawnType = (int)random(0,1);
  }

 
  
 if (zs > 700){
   
   zs = -600; 
   
   index = (int)random(0,Ms.length);
   
   spawnType = (int)random(0,1);
  
   }  
  }
 }
}


class frozenRock {
  
  float r = random(600);
  float y = random(-height);

  public void fall() {
    
    y = y + 7;
   
    image(frozenrock, r, y);

   if(y>height){
     
     r = random(600);
     
     y = random(-200);
   
   }     
  }
  
  public boolean check1(int x2, int y2){
    
  boolean hit = false;
  
  if (this.y>=y2 && this.y<= y2+50){
     
    if (this.r>=x2 && this.r<= x2+50){ hit = true;}
  }
  
  return hit;
  
 }
}

class Rain {
  
  float r = random(600);
  
  float y = random(-height);

  public void fall() {
   
   y = y + 7;
        
   image(fire, r, y);
    
   fire.resize(20, 20);
    
    

   if(y>height){
     
     r = random(600);
     
     y = random(-200);
   
   }     
  }
  
  public boolean check1(int x2, int y2){
    
  boolean hit = false;
  
  if (this.y>=y2 && this.y<= y2+50){
    
     if (this.r>=x2 && this.r<= x2+50){
       hit = true;
     }
  }
  
  return hit;
  
  }
}

class Star {
  
  float r = random(600);
  
  float y = random(-height);

  public void fall(){
    
    y = y + 7;
   
    ellipse(r, y, 2, 2);

   if(y>height){
     
     r = random(600);
     
     y = random(-200);
   }
  }
  public float[] getCoords(){
    
  float[] arr = new float[2];
  
  arr[0] = this.r;
  
  arr[0] = this.y;
  
  return arr;
  
  }
}

class Bullet {
  
  float Z = mouseX ;
  float X = mouseY;

  public void fall() {
    
    X = X - 7;
    image(fire, clicked_at_x, clicked_at_y);}
  }
  
  
public void mouseClicked(){
 
  clicked_at_x = mouseX;
  
  clicked_at_y = yValue;
  
  was_clicked = true;
 
}

public void keyPressed(){
  
  if (key == 'k'){
  
  startX = xValue1;
  
  startY = yValue2;
  
  clicked = true;} 
}

public void displayEndgame(){
  
  fill(200,0,200);
  
  background(img2);

  textSize(50);
 
  text("GAME OVER", width/2 - 150, height/2);
  
  textSize(25);
  
  text("TOTAL SCORE: " + (score-1), width/2 - 100, height/2+35);
  
  text("PRESS Z TO PLAY AGAIN", width/2 - 150, height/2+200);
}

public void displayStart(){
  
  fill(200,0,200);
  
  background(img2);
  
  textSize(50);
  
  text("SPACE SHOOTER", width/2-200, height/2);
  
  textSize(25);
  
  text("PRESS K TO START", width/2 - 120, height/2+35);
  
}

public void checkClick(){
  
 if (mouseX >= width/2 && mouseX <= width+140){
   
    if (mouseY >= height/2 && mouseY <= height/2 + 35){
      
     clicked = true; 
  }
 }
}

public boolean check(int index, int[] Ms, int clicked_at_y, int clicked_at_x, int zs){
  
  boolean hit = false;
  
  int[] cs = Ms;
  
  if (clicked_at_y>= zs - 20 && clicked_at_y <= zs+20){ 
    if (clicked_at_x>= cs[index] - 20&& clicked_at_x <= cs[index] + 20){
      hit = true;
   }
  }  
  return hit;
}



public void removeHealth(){
  fill(200, 0 , 200);
  rect(25, 27, 0 + 2*health, 26);
}
  public void settings() {  size(600,600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "spaceshootergame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

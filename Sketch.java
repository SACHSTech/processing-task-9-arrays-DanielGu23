public class Sketch extends PApplet {
	
  /**
  * A program Sketch.java that simulates falling snow at random locations. 
  * There is a blue circle that represents the player and three squares at the top right to indicate number of lives.
  * If the snow touches the player, the player loses one life.
  * Clicking on a snowflake will cause it to disappear.
  * Pressing the down arrow will cause the snow to fall faster.
  * Pressing the up arrow will cause the snow to fall slower.
  * If the player loses all lives, the game ends and the screen turns white.
  * @author: D. Gu
  */
	
  // Set global variables
  float[] fltSnowY = new float[25];
  float[] fltSnowX = new float[25];
  float fltFallSpeed = 2;
  int intLives = 3;
  boolean isDown = false;
  boolean isUp = false;
  boolean isLeft = false;
  boolean isRight = false;
  float fltPlayerX = 500;
  float fltPlayerY = 500;
  boolean[] isHidden = new boolean[25];
  int intCount = 0;

  /**
   * Sets the size of the call
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void settings() {
    size(1000, 1000);
  }

  /**
   * Creates random values for snowflake locations 
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void setup() {
    background(50);
    for (int i = 0; i < fltSnowY.length; i++){
      fltSnowY[i] = random(height);
    }
    for (int i = 0; i < fltSnowX.length; i++){
      fltSnowX[i] = random(width);
    }
  }

  /**
   * Draws everything inside the method 60 times per second
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void draw() {
    // Set background color
    background(50);
    
    // Draw snowflake at randomly generated location if it is not hidden
    for (int i = 0; i < fltSnowY.length; i++){
      if (isHidden[i] == false){
        fill(255);
        noStroke();
        ellipse(fltSnowX[i], fltSnowY[i], 50, 50);
        fltSnowY[i] = fltSnowY[i] + fltFallSpeed;
      }
      
      // If all the snowflakes have been clicked on, print out "You Win!"
      if (intCount == 25){
        textSize(100);
        text("You Win!", 250, 500); 
        fill(255); 
      }
      
      // If snowflake falls out of screen, make it move back to top
      if (fltSnowY[i] > height){
        fltSnowY[i] = 0;
      }
      
      // Player loses a life if a snowflake touches them
      if (dist(fltSnowX[i], fltSnowY[i], fltPlayerX, fltPlayerY) < 50){
        intLives = intLives - 1;
        fltSnowY[i] = 0;
      }
      
      // If there are no lives left, screen turns white and program stops 
      if (intLives <= 0 ){
        background(255);
        break;
      }
    }
  
    if (keyPressed){
      // Make slowest speed when fltFallSpeed is equal to 1
      // Decreases snowflake fall speed if user presses up key
      if (keyCode == UP && fltFallSpeed > 1){ 
        fltFallSpeed = (float)(fltFallSpeed - 0.05);
      }
      
      // Increases snowflake fall speed if user presses down key
      else if (keyCode == DOWN){
        fltFallSpeed = (float)(fltFallSpeed + 0.05);
      }
    }
    
    // Draw rectangles representing lives and player avatar
    lives();
    fill(0, 0, 128);
    stroke(0);
    ellipse(fltPlayerX, fltPlayerY, 50, 50);
    
    // Draws white screen over everything if no lives remain
    if (intLives <= 0){
      background(255);  
    }
    
    // Depending on w, a, s, d keys, the user will move in a direction
    if (isUp){
      fltPlayerY = fltPlayerY - 5;
    } 
    if (isDown){
      fltPlayerY = fltPlayerY + 5;
    }
    if (isLeft){
      fltPlayerX = fltPlayerX - 5;
    }
    if (isRight){
      fltPlayerX = fltPlayerX + 5;
    }
    
    // Restricts the player, so they cannot leave the screen
    if (fltPlayerX >= 1000){
      fltPlayerX = fltPlayerX - 5;
    }
    else if (fltPlayerX <= 0){
      fltPlayerX = fltPlayerX + 5;
    }
    if (fltPlayerY >= 1000){
      fltPlayerY = fltPlayerY - 5;
    }
    else if (fltPlayerY <= 0){
      fltPlayerY = fltPlayerY + 5;
    }
  }
  
   /**
   * Determines how many rectangles to draw based on number of lives
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void lives() {
    if (intLives == 3) {
      rect(width - 75, 0, 50, 50);
      rect(width - 150, 0, 50, 50);
      rect(width - 225, 0, 50, 50);
    } 
    else if (intLives == 2) {
      rect(width - 75, 0, 50, 50);
      rect(width - 150, 0, 50, 50);
    }
    else if (intLives == 1) {
      rect(width - 75, 0, 50, 50);
    }
  }

  /**
   * Makes the snowflake disappear if the user clicks on it
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void mousePressed() {
    for (int i = 0; i < fltSnowY.length; i++){
      if (dist(fltSnowX[i], fltSnowY[i], mouseX, mouseY) < 25){
        isHidden[i] = true;
        fltSnowX[i] = 0;
        fltSnowY[i] = 0;
        intCount = intCount + 1;
      }
    }
  }
  
  /**
   * Determines if the 'w', 'a', 's', or 'd' keys are being pressed
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void keyPressed() {
    if (key == 'w'){
      isUp = true;
    }
    else if (key == 's'){
      isDown = true;
    }
    else if (key == 'a'){
      isLeft = true;
    }
    else if (key == 'd'){
      isRight = true;
    }
  }
  
  /**
   * Determines if the 'w', 'a', 's', or 'd' keys are released
   * 
   * @param  nothing
   * @return nothing
   * 
   */
  public void keyReleased() {
    if (key == 'w'){
      isUp = false;
    }
    else if (key == 's'){
      isDown = false;
    }
    else if (key == 'a'){
      isLeft = false;
    }
    else if (key == 'd'){
      isRight = false;
    }
  }
  
}
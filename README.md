# ExCELlence
## Northeastern University

## CS3500 - Object Oriented Design
## Scope
The scope of this project is to take in a file with descriptions of shapes and movements, and create a visual animation out of these files.
 
    shape C ellipse
    motion C 6  440 70 120 60 0 0 255

The user may choose which input file to animate, how quickly the animation runs, which "view" of the animation to execute, and where the animation executes to (output file)

## Design
### Model
##### IAnimatorModel

Interface for Model. The Animator Model holds all the Shapes and Directions to be in any individual animation, along with the bounds of the animation canvas.

##### ReadOnlyIAnimatorModel 

The Read Only Interface for the model to be passed into Views so that the Views will only be able to access get methods and not have access to editing the model itself.

Added since Homework 6.

##### AAnimatorModel implements IAnimatorModel

Abstract class for ModelInterface, implements all functions in the Interface. 

Used: 

```java
protected LinkedHashMap<IShape, ArrayList<Direction>> events;
 ``` 

This was used to hold all Shapes and Directions because it was the most straightforward way to keep a long list of Directions for individual Shapes. We decided to make the Hashmap a Linked HashMap because we needed to keep order of shapes in which they are added.

Includes getter methods for all aspects of Animator including returning a copy of the events.This was done so View can later have information without risking changing the original data in the model. Because the model only ever returns copies of objects, the data within the model is safe.

In regards to the changes we made for Homework 6, in the AAnimatorModel, one of the most important changes were adding different get methods for non-primitive types so that unwanted mutations would be prevented. Some of the methods that we created were getCopyDirectionMap(), getCopyDirectionMapRebounded(), getCopyShapeList(). 

In addition to these changes, we added bounds to this class. We did not originally have this because it was not  specified in HW5. However, having bounds is required as specified in all the input .txt files, and as AnimationBuilder specifies. The following lines were added starting at 269 to the end. 

Another change was because we assumed a direction cannot be given out of order. To change this, we made a bit of changes into addDirection of our AAnimator class ( line 95) and added a helper method (line 128) that sorted the ArrayList before adding the directions into the HashMap. Before we added it to the HashMap and all the directions were sorted, we had to check whether or not the starting/end times of each direction was consistent. 

Another important change was optimizing our printing method. Previously, we had a huge block of code that was very inefficient. However, we changed it to be more efficient and a lot shorter than it previously was. We overrided toString() in the Direction class at line 193 which converted the single direction into a toString() and we overrided toString() on line 168 of the AAnimator Model class. This change was mostly because we wanted to code to look cleaner.

In regards to the changes we made for Homework 7, we did not make any changes to any of the methods or to the class itself. However, we did add a few methods such as addKeyFrame, deleteKeyFrame, getClosestDirection, and makeDirection. What each of these methods do are written in the javadocs of the respective methods and it was added because we needed to manipulate keyframes and convert them into directions to add them to the model from the controller. 

##### SimpleAnimatorModel extends AAnimatorModel

Simple class for ModelInterface, has all functionalities of the abstract class. The abstract class was made in case later on we will have to make changes to the model, so additional  or existing methods can simply be overwritten instead of rewritten.

###### Builder

``` java
public static final class Builder implements AnimationBuilder<IAnimatorModel>
```

This class implements the interface AnimationBuilder in a way in which this model understands. It first creates it's own HashMap of events that takes in only Shapes and KeyFrames, and populates this hashmap accordingly. After adding all events, it sorts the KeyFrame, deletes duplicate KeyFrames, and creates directions out of all of the KeyFrames. This way directions can be added out of order without fear of interference. In the `build()` function, it runs through this HashMap and adds every shape and event into the model using the model's add functions. By initially creating a separate hashmap in the Builder, the code does not have to uncessarily access potentially sensitive data in the Model class.

###### Direction
Class to hold a single Direction for a shape (beginning and end) including position, color, width, height and time. Also has method to return a copy itself to prevent muatbility.

In regards to the changes we made for Homework 6, one of the most important changes were adding different get methods for non-primitive types so that unwanted mutations would be prevented. Some of the methods getStartPosition(), getFinalPosition(), getStartColor(), getFinalColor(), and getCopyDirection(). 

Another major change was in the Direction class. We decided to add the startPosition, startWidth, startHeight, and startColor to the class itself. We decided to do this because previously, we would call the Shapes class to get the initial values and because our direction was an ArrayList in AAnimator, we would call (i-1) to get the previous direction. We thought it would be more efficient to have all the information of the shape in the Direction itself and have the Shape only knows its original initialization (so we do not change the values that were initialized in the Shape classes itself). 

Additionally, as mentioned above, another important addition was overriding toString() to return a Direction as a String.

###### KeyFrame
Class to hold KeyFrame for a shape including position, color, width, height, and time. Also has method to return a copy itself to prevent muatbility.

## View
##### IView

Interface for View. Only has one function, `createView()`, as we found views do not share enough other funcitonalities to justify any other shared methods.

##### ISVGView extends IView

Represents the SVG view for the animation. A view is the ways a user can see how a shape move from one state to another. Specific interface for SVGView was created because it had public methods unnecessary in the other views. 

##### IInteractiveView extends IView, ActionListener
Represents an Interactive View for the animation. Plays the Animation as a Visual View and allowsthe user to do actions such as play, pause, loop, increase/decrease speed. Also allows users to add or remove shapes and key frames. User can save their masterpieces after edit as an svg or text file as well. This interface was added because none of the other views had the public methods contained in the interactive view. Additionally, this view is the only view that takes in the actual model itself and not a copy of the model. 

##### SVGView implements ISVGView

SVG View takes in a model, appendable, and tempo, and creates a view accordingly. It uses model's `getCopyDirectionMapRebounded()` function to create a copy HashMap of Shapes and Directions to then create a view off of, all Directions are rebounded so that they are in line relatively to the top left corner of the canvas, which is always (0,0) when the View is created. It has multiple helper functions for each aspect of each shape (ex. Rectangle width, Ellipse x pos) to append SVG format. Since code for each shape is different, the code has if statements for shapes that act accordingly.

##### TextView implements IView
Text view simply returns the toString() of the model.

##### VisualView implements IView, ActionListener 

Creates instance of `VisualPanel`, and starts a timer with the individual "tick duration" specified by the user (given frames/second). With each timer tick, actionPerformed calls on the `VisualPanel` to get the shapes at the given "tick", and repaints accordingly. 

##### VisualPanel extends JPanel 

OverWrites JPanels built in `paintComponent(Graphics g)` to tween all Shapes in Animation at the given time. Whenever timer in `VisualView` increments, so does the `tick` value in `VisualPanel`. Thus, VisualPanel always returns the correct "tween" shapes at the given time.

##### InteractiveView extends JFrame implements IInteractiveView 

Represents an Interactive View for the animator model and plays the Animation as a Visual View. Allows the user to do actions such as play, pause, loop, increase/decrease speed. Also allows users to add or remove shapes and key frames. User can save their masterpieces after edit as an svg or text file as well. Takes in the animator model, the original speed at which it is player, and the controller for the interactive view. For this, we reused the VisualPanel used for VisualView. The rest of the implementation is only similar to that of VisualView in it's necessary parts (in that it plays the VisualPanel). However, it has far more UI elements. We decided to have the InteractiveView control aspect of the view that did not effect the model. Thus, actions play, pause, loop, increase/decrease speed are all facilitated by the Interactive View. We decided to go with this course of action because all these actions are directly affiliated with the timer, an would seem redundant. The following buttons, thus, have the InteractiveView as it's listener:

```java
private JButton playPauseButton;
private JButton loopButton;
private JButton restartButton;
private JButton increaseSpeedButton;
private JButton decreaseSpeedButton;
  ```

When these buttons are pressed, they change the following states:

```java 
  private boolean pause;
  private boolean restart;
  private boolean loop;
  private boolean speedUp;
  private boolean speedDown;
  ```
The `ActionPerformed`, which follows the timer, then constantly checks the state with each tick, and updates the view accordingly, before repainting. For example, if the buttons is at "Pause", it will tell the ActionPanel not to increment which frame it draws before repainting. 

## Shape 
##### IShape
Interface for the different Shapes. Contains get methods that returns information on a shape and an overrided equals method that checks whether or not two shapes are the same shape. 

##### AShape implements IShape
Abstract method that implements the IShape interface. This method contains all of the get methods besides getShape, getCopy and getShape because these are methods that should be in a specific shape and not a general shape. The classes that extend this class will implement and fill in the body of these methods. 

Additionally, because a user can give an ID for a shape without giving any starting value, we have a constructor that assigns default value if the user does not give any. 

##### Ellipse extends AShape
The representation of an Ellipse Shape that extends the AShape class. Has two constructors that call the constructor in the AShape class. Has the methods getShape(), getCopy(), getID() and overridden methods equals() and hashcode() that returns appropriate values that are specific to an Ellipse. 

##### Rectangle extends AShape
The representation of an Rectangle Shape that extends the AShape class. Has two constructors that call the constructor in the AShape class. Has the methods getShape(), getCopy(), getID() and overridden methods equals() and hashcode() that returns appropriate values that are specific to an Rectangle. 

## Controller
##### IController extends ActionListener
Interface that controls the interactive view and allows users to change the original animation model through the interactive view. Contains one method executeInteractiveView()and overrides actionPerformed(ActionEvent e). 

##### Controller implements IController
Implements the IController interface. Class to control interactive view and takes in an IAnimator model and the speed at which the animation initially run. Allows users to change the original animation model through the interactive view. Controller creates an InteractiveView with `executeInteractiveView()`. It is also the ActionListener for any buttons in the Interactive View that directly control the model, or aspects of the code outside of the View. The following buttons have Controller as a listener:
```java
  private JButton deleteShapeButton;
  private JButton addKeyFrameButton;
  private JButton deleteKeyFrameButton;
  private JButton addShapeButton;
  private JButton saveAnimationButton;
  ```
The ActionPerformed edits the model accordingly and creates a new InteractiveView with each change to the model. Although the view takes in the Controller as the listener, this should not have any mutation issues, because the controller if of type ActionListener, and therefore only has the functionalities of an ActionListener in the view (can only execute ActionPerformed). Thus, any of the other functionalities of the Controller cannot be execute by View. 

## Main
##### Excellence
This `main()` method is the entry point for our program. The user can specify command-line arguments, such as:
- the file to be read in, with `-in`
- the out file to write to, with `-out`
- the speed in frames/second in which the animation is executed, with `-speed`
- the view type to be chosen, with  `-view`
    - user can choose between `svg`, `text`, `visual`, and `edit` 

Examples:

- `-in smalldemo.txt -view text -speed 2`
    - this uses smalldemo.txt for the animation file, and creates a text view with its output going to System.out, and a speed of 2 ticks per second.
- `-view svg -out out.svg -in buildings.txt`
    - this uses buildings.txt for the animation file, and creates an SVG view with its output going to the file out.svg, with a speed of 1 tick per second.
- `-in smalldemo.txt -view text`
    - this uses smalldemo.txt for the animation file,and creates a text view with its output going to System.out.
- `-in smalldemo.txt -speed 50 -view visual`
    - this uses smalldemo.txt for the animation file, and create a visual view to show the animation at a speed of 50 ticks per second.
- `-in buildings.txt -speed 60 -view edit`
    - this uses buildings.txt for the animation file, and creates an interactive view at the speed of 50 ticks per second. User can not edit shapes, keyframes, and play/pause/loop/control speed of the animation.

If the command-line arguments are anything but these, the program will throw an error message.

## How To Use
1. Create an animation file by creating a text file
    - create a canvas, like so:
    `canvas 145 50 410 220`
    where 145 and 50 are the x and y coordinate of the upper lefthand side of the canvas, respectively, 410 is the weidth of the canvas, and 220 is the height.
    - create any number of shapes, like so:
    `shape R rectangle`
    `shape E ellipse`
    where R and E are the IDs of the shape, and should always be a unique name. `rectangle` and `ellipse` are the only legal shape types thus far.
    - create motions for shapes, like so:
    `motion E 1 190 161 20 11 113 87 151  1 190 161 20 11 113 87 151`
    where E is the ID of the shape, and the following number are respectively from left to right:
        - start time,start x position, start y position, start width, start height, start red value (between 0-255),  start green value (between 0-255), start blue value (between 0-255), end time, end x position, end y position,end width, end height, end red value (between 0-255), end green value (between 0-255), end blue value (between 0-255)
    - please be sure that start and end times for separate directions of the same shape do not overlap, and additionally that start and end positions/colors/sizes are consistent so that there or no jumps
    - otherwise code will throw an error
1. Copy jar file and animation input file to same folder.
1. Open command-prompt/terminal and navigate to this folder.
1. Type:
`java -jar NameOfJARFile.jar` 
followed by any of the parameters the user desires.
(Please see main for legal commands)
1. Press enter
1. Enjoy your animation!

## How to Use Interactive View
1. Repeat steps for How to Use, using edit as view.
- Add Shape
    - Next to `Type`, select the type of new Shape you want. You can choose between Rectangle and Ellipse
    - Next to `ID`, type in the name of the Shape. 
    - Click the `Add Shape` button once you are done. The shape will now appear in the drop down of existing shapes if you want to delete/edit the Shape.
- Delete Shape
    - Select the name of the Shape you want to delete in the dropdown.
    - Click the `Delete Shape` button.
- Edit Shapes
    - If you want to add a Keyframe, select a Shape and fill in all the parameters of the new Keyframe you would like to add. Click `Add Keyframe`. The Shape you selected should now have the added Keyframe.
    - If you want to delete a Keyframe, select a Shape and fill in the time of the Keyframe you want to delete. Click `Delete Keyframe`. The keyframe should now be deleted from the Shape you have selected.
- Save Animation
    - Select the format you would want to save the animation in from the dropdown.
    - Fill in the name of the file you want your animation to be saved in. No need to add `.txt` or `.svg`, the program will do that automatically. Simply type in the name.
    - Click `Save Animation`. Your masterpiece is now saved!



    



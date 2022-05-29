# RasmView
RasmView is an Android drawing library; it provides a view that allows users to draw on top of a bitmap.

## Demo
[https://www.youtube.com/watch?v=8qYhwjleT_8](https://www.youtube.com/watch?v=8qYhwjleT_8)

![Screenshot](https://raw.githubusercontent.com/Raed-Mughaus/RasmView/main/sample_screenshot.jpg)

## Features
* 8 already defined brushes, and you can define your own.
* Drawing on top of images.
* Undo/redo operations.
* Zooming in/out, rotation & translation.
* Custom background color.


## Download
#### Gradle:
```gradle
dependencies {
  implementation 'com.raedapps:rasmview:1.2.0'
}
```
#### Maven:
```xml
<dependency>
  <groupId>com.raedapps</groupId>
  <artifactId>rasmview</artifactId>
  <version>1.2.0</version>
</dependency>
```
## Usage Guide
#### Add the following to your layout file:
```
<com.raed.rasmview.RasmView
  android:id="@+id/rasmView"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  />
```
#### RasmContext
`RasmContext` allows you to control the brush configuration, undo/redo, reset transformation, and more. `RasmContext` can be accessed from `RasmView`:
```kotlin
val rasmView = findViewById<RasmView>(R.id.rasmView)
val rasmContext = rasmView.rasmContext
```
#### Changing the brush
You can use the `BrushesRepository` to get an already defined brush.
```kotlin
val brushesRepository = BrushesRepository(resources)
rasmContext.brushConfig = brushesRepository.get(Brush.Marker)
```
The following brushes are already defined:
```kotlin
enum class Brush {
    Pencil,
    Pen,
    Calligraphy,
    AirBrush,
    Marker,
    HardEraser,
    SoftEraser,
}
```

#### Brush color
Here is how to change the brush color:
```kotlin
rasmContext.brushColor = Color.RED
rasmContext.brushColor = 0xff2187bb.toInt() //ARGB
```
The alpha channel value is ignored, you can control alpha by setting `brushConfig.flow`.
#### Brush size and other configurations
```kotlin
val brushConfig = rasmContext.brushConfig
brushConfig.size = 0.5f
brushConfig.flow = 0.25f
brushConfig.isEraser = true
```
#### Custom brushes
```kotlin
val myStampBitmap = ...
val customBrushConfig = BrushConfig()
customBrushConfig.stamp = BrushStamp.BitmapStamp(myStampBitmap)
customBrushConfig.size = 0.25f
customBrushConfig.spacing = 0.1f
rasmContext.brushConfig = customBrushConfig
```
#### Drawing on a bitmap (your own image).
```kotlin
val imageBitmap = ... //load your bitmap whether from a URI or resources
rasmContext.setRasm(imageBitmap)
rasmView.resetTransformation() 
```
#### Getting the drawing
```kotlin
val drawingBitmap = rasmContext.exportRasm()
```
#### Background color
```kotlin
rasmContext.setBackgroundColor(Color.BLACK)
```
#### Undo/redo
```kotlin
val rasmState = rasmContext.state
rasmState.undo()
rasmState.redo()
```
But you do not want to keep your buttons enabled when an undo/redo is not possible, you can listen to state updates:
```kotlin
undoButton.setOnClickListener {
    rasmState.undo()
}
redoButton.setOnClickListener {
    rasmState.redo()
}
rasmState.addOnStateChangedListener {
    undoButton.isEnabled = rasmState.canCallUndo()
    redoButton.isEnabled = rasmState.canCallRedo()
}
undoButton.isEnabled = rasmState.canCallUndo()
redoButton.isEnabled = rasmState.canCallRedo()
```
#### Clearing the drawing
```kotlin
rasmContext.clear()
```
#### Enabling rotation
```kotlin
rasmContext.rotationEnabled = true
```
#### Resetting the transformation
```kotlin
rasmView.resetTransformation()
```


## License
```
Copyright 2022 Raed Mughaus

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

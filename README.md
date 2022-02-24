# RasmView
RasmView is an Android drawing library; it provides a view that allows users to draw on top of a bitmap.

WARNING 1: The library is new and needs testing. If you found a bug, please open an issue.

WARNING 2: Some breaking changes might be introduced in the coming days.

## Demo
[https://www.youtube.com/watch?v=8qYhwjleT_8](https://www.youtube.com/watch?v=8qYhwjleT_8)

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
  implementation 'com.raedapps:rasmview:1.0.1-beta'
}
```
#### Maven:
```xml
<dependency>
  <groupId>com.raedapps</groupId>
  <artifactId>rasmview</artifactId>
  <version>1.0.1-beta</version>
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
RasmContext allows you to control the brush configuration, undo/redo, reset transformation, and more. You can access an already initialized rasm context from your rasmView:
```kotlin
val rasmView = findViewById<RasmView>(R.id.rasmView)
val rasmContext = rasmView.rasmContext
```
#### Changing the brush
You can use the BrushesRepository to get an already defined brush.
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
```kotlin
rasmContext.brushColor = Color.RED
```
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
val imageBitmap = ...
val rasmContext = RasmContext()
rasmContext.init(imageBitmap)
rasmView.rasmContext = rasmContext
```
#### Getting the drawing
```kotlin
val drawingBitmap = rasmView.rasmContext.rasmBitmap
```
#### Background color
```kotlin
rasmContext.setBackgroundColor(Color.BLACK)
```
#### Undo/redo
```kotlin
val rasmState = rasmContext.state
undoButton.setOnClickListener {
    rasmState.undo()
}
redoButton.setOnClickListener {
    rasmState.undo()
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

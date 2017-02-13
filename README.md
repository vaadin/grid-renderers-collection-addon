# Grid Renderers Add-on for Vaadin 7

Grid Renderers collection is a set of renderers for Vaadin 7 Grid.

## Online demo

Try the add-on demo at *TBD*

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to https://vaadin.com/directory#!addon/grid-renderers-collection-for-vaadin7

## Building and running demo

git clone https://github.com/vaadin/grid-renderers-collection-addon.git
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for grid-renderers-collection-addon-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your grid-renderers-collection-addon-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the grid-renderers-collection-addon-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/grid-renderers-collection-addon-demo/ to see the application.

### Debugging client-side

The most common way of debugging and making changes to the client-side code is dev-mode. To create debug configuration for it, open grid-renderers-collection-addon-demo project properties and click "Create Development Mode Launch" button on the Vaadin tab. Right-click newly added "GWT development mode for grid-renderers-collection-addon-demo.launch" and choose Debug As > Debug Configurations... Open up Classpath tab for the development mode configuration and choose User Entries. Click Advanced... and select Add Folders. Choose Java and Resources under grid-renderers-collection-addon/src/main and click ok. Now you are ready to start debugging the client-side code by clicking debug. Click Launch Default Browser button in the GWT Development Mode in the launched application. Now you can modify and breakpoints to client-side classes and see changes by reloading the web page. 

Another way of debugging client-side is superdev mode. To enable it, uncomment devModeRedirectEnabled line from the end of DemoWidgetSet.gwt.xml located under grid-renderers-collection-addon-demo resources folder and compile the widgetset once by running vaadin:compile Maven target for grid-renderers-collection-addon-demo. Refresh grid-renderers-collection-addon-demo project resources by right clicking the project and choosing Refresh. Click "Create SuperDevMode Launch" button on the Vaadin tab of the grid-renderers-collection-addon-demo project properties panel to create superder mode code server launch configuration and modify the class path as instructed above. After starting the code server by running SuperDevMode launch as Java application, you can navigate to http://localhost:8080/grid-renderers-collection-addon-demo/?superdevmode. Now all code changes you do to your client side will get compiled as soon as you reload the web page. You can also access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings. 

 
## Release notes

### Version 2.0.0.beta
* Migration to Vaadin 8.beta2

### Version 0.94
* RatingStarsRenderer added
* Updated the demo to use RatingStarsRenderer
* Vaadin version advanced to 7.6.8

### Version 0.93
* Vaadin version advanced to 7.6.7
* SparklineRenderer added
* Renderers are split to view, editable and editor-aware
* Refactored the demo
* Updated documentation

### Version 0.92
* Vaadin version advanced to 7.5.7
* New renderers added
* Renderers are split to editable and editor-aware

### Version 0.91
* Vaadin version advanced to 7.5.6
* Fixed IllegalStateException

### Version 0.9
* CheckboxRenderer - renderer for boolean columns. Looks like checkbox, activates edit mode at first click. Supposed to be used with Grid unbuffered mode

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

Grid Renderers collection is initially written by Ilia Motornyi(elmot@vaadin.com)

DateFieldRenderer, TextFieldRenderer, SparklineRenderer, & RatingStarsRenderer Contributed by Tatu Lund(tatu@vaadin.com)

# Developer Guide

## Getting started

Here is a simple example on how to try out the add-on component:

    grid.getColumn("yes").setRenderer(new CheckboxRenderer());

For a more comprehensive examples, see org.vaadin.grid.cellrenderers.demo.DemoUI

## Features

### CheckboxRenderer
Single-click editor for boolean columns - Editor aware. Most suitable for unbuffered Grid(Vaadin 7.6+)

### DateFieldRenderer
Inline Dates editor.

### TextFieldRenderer
Multipurpose inline Text editor. Supports various types of data using com.vaadin.data.util.converter.Converter TextFieldRenderer and DateFieldRenderer are suitable when
you need to edit few columns only. Tab key jumps between editable fields by default. The input is
directly stored in the container. For backend commits it is recommend to have "save" button in the
UI.

### SparklineRenderer
Configurable SparklineRenderer. Renders collection of Numbers as a simple chart. The renderer uses
Sparklines add-on by Marc Englund (which inturn uses gwt-graphics add-on). SparklineRenderer has
SparklineConfiguration class inside, which controls various Sparkline configuration options thru
shared state. Most of the settings have immediate effect, see the demo.

### RatingStarsRenderer
RatingStarsRenderer is based on Widget in RatingStars add-on by Teemu P&ouml;ntelin. You can use 
RatingStarsRenderer both as a view only or editable field renderer. The max number of stars can
be also configured.



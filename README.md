Glitcherator - from [y_a_v_a](http://www.y-a-v-a.org)
=====================================================

Glicherator is an image manipulation program to distort JPEG images into glitched images, which in turn can be saved. You can open images and modify a couple of options to influence the effect of glitching. If glitching fails due to the fact that your changes created an unreadable JPEG stream, the program falls back to the original opened image.

This app creates glitches by replacing certain amounts of values of a hexadecimal representation of the supplied image. The amount of replacements (which I call chunks), the size of the chunks and the replacement value (a hexadecimal value ranging from 00 to FF) are changeable by dragging the sliders at the top bar. The Refresh button can be used to create a new random glitched version of the supplied image.

### Additional Libraries ###
Glitcherator uses the [Apache Commons Codec](http://commons.apache.org/codec/) package. The [API Documentation is available](http://commons.apache.org/codec/apidocs/org/apache/commons/codec/binary/Hex.html).

### Build ###
Glitcherator is written using Eclipse IDE. The Apache Commons Codec library should be referenced. You can run the application from within Eclipse by clicking Run -> Run from the main menu.
If you want to create a portable version, you can create a JAR-file by exporting the project to a 'Runnable JAR file'.
On Mac OSX you can then use the Jar Bundler to create a stand alone application, available from:
```bash
user/share/java/Tools/Jar Bundler
```
After creating a Jar Bundle you can put Glitcherator within your Applications folder and start it by double clicking the application icon.

### Icon ###
Icons are available from the `icons` folder. A Mac OSX complient icon for use in the Jar Bundler is also available from the `icons` folder.
![](https://raw.github.com/y-a-v-a/glitcherator/master/icons/g128.png)

### Wishlist ###
* Support for other file types
* Batch processing
* Command line tool for easy batching
* Input field for custom replacement string
* Option to add or subtract data instead of replace

### License ###

From within the Glitcherator application a window is accessible containing the GPL v3 which applies to this code.

### Contributors ###

* Vincent Bruijn aka y_a_v_a aka Yet Another Visual Artist

### Inspiration ###
* Inspired by [Monglot](http://rosa-menkman.blogspot.nl/search/label/Monglot) of Rosa Menkman (thanks for open sourceing Monglot so I could take a look at the code)
* Satromizer, the iOS app by Jon Satrom.
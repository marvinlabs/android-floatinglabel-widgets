Android Floating Label Widgets
==============================

A set of input widgets with a hint label that floats when input is not empty.

## Demo

A demo of the widget is worth a thousand words. You can download it for free on Google Play.

<a href="https://play.google.com/store/apps/details?id=com.marvinlabs.widget.floatinglabel.demo">
  <img alt="Demo on Google Play"
         src="http://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

We also have a small video showing it

[![demo video](http://img.youtube.com/vi/hpZD9gJcRg0/0.jpg)](http://youtu.be/hpZD9gJcRg0)

## Usage

### Including the library

The easiest way to get the library included in your project is by using Gradle. Simply add the 
following line to your dependencies block:

```groovy
dependencies {
    compile 'com.marvinlabs:android-floatinglabel-widgets:1.0.+@aar'
}
```
    
Of course, you can replace the version number by whichever version you need (you can have a look at 
this repository's tags to know which is the latest).

### Getting a floating label widget in your fragment/activity

#### EditText

To include a floating label EditText in your layout, simply use the following XML code snippet:

```xml
<!-- An edit text -->
<com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="15dp"
    app:flw_labelText="Simple text input" />

<!-- An edit text with a custom input type and an icon on the left -->
<com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:drawableLeft="@drawable/ic_lock"
    android:drawablePadding="10dp"
    android:inputType="textPassword"
    app:flw_labelText="Password input" />
```

#### ItemPicker

To include a floating label ItemPicker in your layout, simply use the following XML code snippet:

```xml
<!-- A widget that shows the result of item selection -->
<com.marvinlabs.widget.floatinglabel.picker.FloatingLabelItemPicker
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:flw_labelText="Item picker" />
```

We then need some code to bring up the item picker. The library ships with simple DialogFragment
picker implementations. Of course, you are free to roll your own pickers. Here is how we setup the
widget in the demo activity:

```java
public class MainWidgetsActivity extends FragmentActivity implements ItemPickerListener<String> {
    FloatingLabelItemPicker<String> picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity layout
        setContentView(R.layout.activity_main_widgets);

        // Spinners
        picker = (FloatingLabelItemPicker<String>) findViewById(R.id.picker1);

        // These are the items we want to be able to pick
        picker.setAvailableItems(new ArrayList<String>(Arrays.asList("Item 1.1", "Item 1.2", "Item 1.3")));

        // We listen to our pickerWidget events to show the dialog
        picker.setWidgetListener(new FloatingLabelItemPicker.OnItemPickerWidgetEventListener<String>() {
            @Override
            public void onShowItemPickerDialog(FloatingLabelItemPicker<String> source) {
                // We use fragments because we'll be safe in edge cases like screen orientation
                // change. You could use a simple AlertDialog but really, no, you don't want to.
                StringPickerDialogFragment itemPicker = StringPickerDialogFragment.newInstance(
                        source.getId(),
                        "My dialog title",
                        "OK", "Cancel",
                        true,
                        source.getSelectedIndices(),
                        new ArrayList<String>((Collection<String>) source.getAvailableItems()));
                itemPicker.show(getSupportFragmentManager(), "ItemPicker");
            }
        });
    }

    // Implementation of the ItemPickerListener interface: those two methods get called by the
    // ItemPicker automatically when something happens

    @Override
    public void onCancelled(int pickerId) {
    }

    @Override
    public void onItemsSelected(int pickerId, int[] selectedIndices) {
        picker.setSelectedIndices(selectedIndices);
    }
}
```

## Customisable components

This library has lots of interfaces and helper classes to help you either extend it and/or change
the existing behaviour.

### LabelAnimator

This handles the way labels get animated when transitioning from anchored to the input widget to
floating above it.

### ItemPrinter

This is used by the item picker widgets and dialogs to simply convert an object to a String
representation. This is in case you would not want to use the toString method to display your
objects.

### ItemPicker

This defines what an item picker should be able to do. We have provided an implementation in the
form of a DialogFragment. You could however roll your own implementation for instance to use
a custom dialog, a date/time picker, etc.

## About Vincent & MarvinLabs

I am a freelance developer located in Biarritz, France. You can 
[have a look at my website](http://vincentprat.info) to get to know me a little better. If you want 
to follow me, here are some links:

* [Follow me on Twitter](http://twitter.com/vpratfr)
* [Follow me on Google+](https://plus.google.com/+VincentPrat)
* [Follow me on Facebook](http://www.facebook.com/vpratfr)

MarvinLabs is my digital studio specialised in native mobile applications and web sites. You can 
[browse our website](http://www.marvinlabs.com) to get to know us a little better. If you want to 
get updates about our work, you can also:

* [Follow us on Twitter](http://twitter.com/marvinlabs)
* [Follow us on Google+](https://plus.google.com/+Marvinlabs)
* [Follow us on Facebook](http://www.facebook.com/studio.marvinlabs)

## Change log

### 1.0.0 (2014-08-29)

  - First release (beta)
  - Two floating label widgets for a start (EditText and ItemPicker)
  - Demo application

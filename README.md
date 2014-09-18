Android Floating Label Widgets
==============================

A set of input widgets with a hint label that floats when input is not empty.

## Demo

A demo of the widget is worth a thousand words. You can download it for free on Google Play.

<a href="https://play.google.com/store/apps/details?id=com.marvinlabs.widget.floatinglabel.demo">
  <img alt="Demo on Google Play"
         src="http://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

We also have a small video showing it (just click the image below)

[![demo video](http://img.youtube.com/vi/hpZD9gJcRg0/0.jpg)](http://youtu.be/hpZD9gJcRg0)

## Usage

### Including the library

The easiest way to get the library included in your project is by using Gradle. Simply add the 
following line to your dependencies block:

```groovy
dependencies {
    compile 'com.marvinlabs:android-floatinglabel-widgets:1.3.2@aar'
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
<com.marvinlabs.widget.floatinglabel.itempicker.FloatingLabelItemPicker
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
                
                // Optionally, you can set a target fragment to get the notifications
                // pickerFragment.setTargetFragment(MyFragment.this, 0);
                
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

#### Instant pickers (DatePicker and TimePicker)

To include a floating label instant picker in your layout, simply use the following XML code snippet:

```xml
<!-- A widget that shows the result of item selection -->
<com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelDatePicker
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:flw_labelText="Date picker" />
    
<!-- A widget that shows the result of item selection -->
<com.marvinlabs.widget.floatinglabel.instantpicker.FloatingLabelTimePicker
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:flw_labelText="Time picker" />
```

We then need some code to bring up the instant pickers. The library ships with simple system
picker implementations. Of course, you are free to roll your own pickers and/or use another library
([Better pickers](https://github.com/derekbrameyer/android-betterpickers) is great). Here is how 
we setup the widget in the demo activity:

```java
public class MainWidgetsActivity extends FragmentActivity implements InstantPickerListener<TimeInstant> {
    FloatingLabelTimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity layout
        setContentView(R.layout.activity_main_widgets);

        // Spinners
        timePicker = (FloatingLabelTimePicker) findViewById(R.id.time_picker);
                
        // We listen to our pickerWidget events to show the dialog
        timePicker.setWidgetListener(new FloatingLabelInstantPicker.OnWidgetEventListener<TimeInstant>() {
            @Override
            public void onShowInstantPickerDialog(FloatingLabelInstantPicker<TimeInstant> source) {
                TimePickerFragment pickerFragment = TimePickerFragment.newInstance(source.getId(), source.getSelectedInstant());
                
                // Optionally, you can set a target fragment to get the notifications
                // pickerFragment.setTargetFragment(MyFragment.this, 0);
                
                pickerFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });
    }

    // Implementation of the InstantPickerListener interface

    @Override
    public void onInstantSelected(int pickerId, TimeInstant instant) {
        timePicker.setSelectedInstant(instant);
    }
}
```

#### Item choosers

To include a floating label item chooser in your layout, simply use the following XML code snippet:

```xml
<!-- A widget that shows the result of item selection -->
<com.marvinlabs.widget.floatinglabel.itemchooser.FloatingLabelItemChooser
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:flw_labelText="Choose a product" />
```

This kind of widget lets you choose an item the way you want. The demo application shows how to 
implement item choosing by launching an activity and getting the result back.

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

### 1.4.0 (2014-09-18)

  - Added listener interfaces for notifications when the item/instant/text changes within one of the
    widgets

### 1.3.2 (2014-09-11)

  - Allow setting the input widget text size and color from XML
  - Added a more general purpose item chooser widget to let you handle everything

### 1.2.4 (2014-09-03)

  - Fix a potential NullPointerException in FloatingLabelItemPicker#getSelectedItems

### 1.2.2 (2014-09-02)

  - Correct the Parcelable implementation for JavaTimeInstant and JavaDateInstant classes
  - Allow setting a target fragment to receive the events
  - Fix a bug with instant fragments listeners not being notified 
  - Improved the demo
   
### 1.2.0 (2014-09-01)

  - Making the instant pickers fully use generics so that we can use our own Instant implementations
    (e.g. joda time)

### 1.1.0 (2014-09-01)

  - Adding time and date floating label pickers with system picker implementation for the dialogs
  - Allow the AbstractPickerDialogFragment to send its events to the parent fragment too if that one
    implements the ItemPickerListener interface (not only the parent Activity) 
  - Removed unused attr "labelAllCaps", could be reintroduced later if requested
  - Renamed OnItemPickerWidgetEventListener to OnWidgetEventListener for concision
  - Fix #3

### 1.0.0 (2014-08-29)

  - First release (beta)
  - Two floating label widgets for a start (EditText and ItemPicker)
  - Demo application

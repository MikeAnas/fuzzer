![bunq](https://raw.github.com/bunqcom/fuzzer/master/logo.jpg)

# bunq fuzzer

## Description
This repo holds two connected applications.
The fuzzer-android-server which is a server that runs on an Android device, and gets instructions from the client. 
These instructions are dispatched to UiAutomator to perform UI actions in the device.
The bungFuzzer-pc-client reads xml files and parses them to instructions which are send to the server.

## Usage
First the fuzzer-android-server needs to be build into a .jar file.
This jar file needs to be pushed to the Android device.
And then it needs to be started with a uiAutomator command.

All to build and start this server are scripted together in the buildnstart.sh file.

```
./buildnstart.sh
```

If you just want to start the server (the .jar file should then already be on the device) run:

```
./restart.sh
```

After the server is started you can start the client. 
The client will automatically start parsing the xml files and send the instructions to the server.

### Design Goals
This Section is part of our full report.

#### 

With our fuzzing tool we want to make a proof of concept for our idea of
fuzzing that is not completely random, but follows a given structure. To
emphasize this point the fuzzing tool will not be specific for the bunq
app or OS. However, the tool has been configured in such a way that the
Android bunq app can be tested. However, a user of the tool should be
able to change this configuration to test other apps on both Android and
iOS.

#### 

Obviously, the problems that were encountered by using the existing
tools should be avoided. This will be done by following the methods
described in Section \[sec:testing\]. The three drawbacks of the
existing tools will be countered in the following ways:

Documentation and Version

:   \
    The tool should be well documented and work with the latest version
    of Android. The tool should be easy to configure for different apps.
    This is accomplished by writing a readme that fully explains how the
    tool has to be set up and which options can be configured. For
    developers, it should be easy to modify the source code, so that
    they can get the tool to work under other operation systems. This is
    done by creating javadoc for all the methods and classes. In this
    way the developers are able to fully understand the source code of
    the tool and thus change it if they need to.

Randomness

:   \
    The randomness of the tool should be able to be configured according
    to the tester’s wishes. This can be done by the use of XML
    configuration files. These are the files which the testers need to
    write specifically for the app. In these files he should be able to
    tell the tool what testing paths it should take and how much random
    actions it should take in between.

Recognizing Bugs

:   \
    The tool should be able to recognize system crashes and
    functional bugs. It should also report what steps it took to produce
    these bugs, so that they can be reproduced later on.
    
## Design 
------
This Section is part of our full report.

#### 

Since not all of the written code is useful to discuss, only some
highlights are portrayed in this section. This includes the parts for
which design choices needed to be made, or where special code structures
were used. The following section will layout the global set-up of the
fuzzing tool. The sections thereafter will go further into the different
parts of the tool; this includes the configuration files and package
structure.

### Global Set-up

#### 

The fuzzer exists out of three parts. The first part are the XML files.
These files are created by the tester and hold paths through the app
that the fuzzer should test with a certain chance. The other two parts
are two programs. 

#### 

One program, the fuzzer-pc-client runs on a computer. It parses the
XML files to actions and input objects and sends these objects as UI
instructions to the server.

#### 

The other program, called fuzzer-android-server, is a server that
runs on the android device which runs the app under test. The
fuzzer-android-server application gets instructions from the
fuzzer-pc-client. These instructions are executed by the
fuzzer-android-server on the device.

#### 

Because of the division into two programs it is also possible to make a
server on iOS or Windows Phone which translates the same instructions
from the client to UI interactions. Although the earlier described FSM
learning application uses Appium to execute its UI interactions, the
fuzzer uses UIAutomator to execute actions on Android devices. Appium
makes it easier to make the translation from Android to another
operating system, but it also slows down the application. Because
fuzzing uses random operations which do not always make sense, you want
to do a lot of them. Therefore it is important that you are able to send
a lot of instructions within a certain time period.

### Configuration Files

#### 

The fuzzer is instructed by XML files. These files are created by the
tester. They hold information about all the possible actions that can be
executed on the app under test. Appendix \[app:xmlfile\] shows an
example of such an XML file.

#### 

The root node of these XML files is an `actionset` node. An `actionset`
holds other actionsets and actions. An `action` is a single UI
instruction that can be executed on the device. Sometimes an `action`
holds an input. For example, when something needs to be entered into a
textfield the `action` and its input could look like this:

``` {breaklines=""}
<action operation="insert" id="com.bunq.android:id/etNumber"> 100 </action>
        
```

An input for such a field could also exist out of an `inputlist`, then
it would look like this:

``` {breaklines=""}
<action operation="insert" id="com.bunq.android:id/etNumber"> <file name="number.xml" /> </action>
        
```

An `inputlist` is an separate XML file which holds multiple inputs. When
an `action` is sent to the device to be executed one input out of its
`inputlist` is randomly chosen.\
\
There are three attributes that can be set to an `actionset`:\
**chance** indicates the probability that the actions that this
`actionset` hold are sent to the device.\
**times** is the amount of times the actions that this `actionset` holds
are executed.\
**order** can be set to one, random or inorder:\
**one** indicates that only one `action` in this `actionset` will be
sent to the device.\
**random** indicates that the actions will be sent in a random order.\
**norder** indicates that the actions will be sent in the order they
appear in the XML file.\
\
An `action` itself can also have three attributes:\
**chance** if it is set the action will only be sent with this
probability to the device.\
**operation** the kind of UI operation that the device should do.\
**id** the id of the UI element that the operation should be executed
on.

#### 

An input can also hold a chance parameter. This indicates the chance
that this input is selected out of the inputlist to act as the actual
input of an `action` when it is executed on the device.

### fuzzer-pc-client

#### 

The fuzzer-pc-client has three functions. It has to read the XML
files and parse them to action objects. The second function is holding
the action objects and translating them to ActionInstructions while
taking their chances into account. Finally, it has to send these
ActionInstructions to the server. These functionalities are separated by
the use of packages.

com.bunq.parsers

:   \
    This package holds two parsers: the ParseActionXML and
    ParseInputXML classes. These classes both extend the abstract
    class ParseXML. ParseXML holds functions that both of the parser
    classes need.

    ParseActionXML uses recursion to make a tree structure of all
    actionsets and actions of the XML file it is reading. The
    ParseActionXML class can return the root actionset. To keep a clean
    structure in the XML files it is possible to nest files in
    one another. ParseInputXML is called by ParseActionXML when it
    encounters an inputlist file. It creates an inputlist object and the
    input objects that it holds. And returns this inputlist to
    ParseActionXML which on his turn adds this inputlist to the action
    object that it should hold.

com.bunq.actions

:   \
    The actions package holds the ActionSet, Action, InputList and
    Input classes. These are all the classes needed to create
    ActionInstructions objects that can be sent to the server. An
    ActionSet object holds other ActionSets and Actions. An Action
    object can hold an Input or an InputList with Inputs.

    ActionSet and Actions implement the interface IAction. This means
    that they both should have an `createActionInstructionList()` method
    that returns an ArrayList of ActionInstructions. When this method is
    called on the root ActionSet it recursively calls the
    `createActionInstructionList()` method of all the actions it holds.
    All the lists of ActionInstructions are recursively combined in one
    ArrayList of ActionInstructions, which can be sent to the server.

    `createActionInstructionList()` calculates in which order the
    Actions of an ActionSet should be sent and how many times they
    should be sent. It also calculates the probability whether they
    should be sent. For Actions holding an Inputlist, it asks the
    InputList to calculate which input should be used.

com.bunq.communication

:   \
    The communication package holds the ActionInstruction class that can
    be created by the Actions. An ActionInstruction is the object that
    is actually sent to the server. It is a different object than the
    Action objects themselves because it has to be much smaller and
    should only hold the data that the device needs to execute the
    UI operation.

    ClientSocketWrapper is the other class in communication which sends
    ActionInstructions to the server over a socket and returns the
    response from the server.

### fuzzer-android-server

#### 

The fuzzer-android-server has two functions. First it needs to start
a server socket to communicate and receive ActionInstruction objects
from the client. Secondly it has to execute these instructions; this is
done by UIAutomator. These two functions are again separated by the use
of packages.

#### 

The server is kept really small on purpose. By keeping it small and
letting the client do all the calculations and parsing of XML files it
will be easier to implement another server for iOS or another OS in the
future.

com.bunq.communication

:   \
    This package holds the ActionInstruction class, which is the same as
    on the client. An instance is created when an ActionInstruction is
    received by the ServerSocketWrapper. The ServerSocketWrapper
    connects with the ClientSocketWrapper on the client side..

com.bunq.driver

:   \
    This package holds two classes that extend UiAutomatorTestCase and
    can be started on an Android device by an adb call. UIAutomator is
    integrated in Android. It makes it possible to trigger UI
    interactions without physical interaction.


## About
This application was written by Tom den Braber, Michel Kraaijeveld and Kees Lampe as part of a BSc final project.

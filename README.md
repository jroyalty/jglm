JGLM - Java OpenGL Mathematics Library
======================================

A *conceptual* port of the [OpenGL Mathematics](http://glm.g-truc.net/) C++ 
library (GLM).  I call this conceptual because a direct port of GLM to Java wouldn't
make a ton of sense.  So, instead, I've taken the concept of providing some of
the functionality of [GLSL](http://www.opengl.org/documentation/glsl/) and
the missing matrix capabilities of modern OpenGL and move those to Java.

The current version is **1.0.0**.


Using
-----

JGLM is available in Maven Central so just add this to your POM (or adapt to whatever build tool you use that supports Maven artifacts):

```
<dependency>
    <groupId>com.hackoeur</groupId>
    <artifactId>jglm</artifactId>
    <version>1.0.0</version>
</dependency>
```

Building
--------

JGLM does not require any external libraries!  The only things you'll need are

* Java 1.6 or greater and
* Maven 3.x

In order to build just clone the repository and run:

```
mvn clean install
```

Legal stuff
-----------

Copyright (C) 2013, 2014, 2015 James Royalty

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


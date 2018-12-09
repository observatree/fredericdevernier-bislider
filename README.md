The BiSlider Project by Frederic D. E. Vernier
==============================================

This project is derived from BiSlider.jar downloaded from
[Frederic Vernier's BiSlider website](https://perso.limsi.fr/vernier/BiSlider/)
on 2018-12-09.

The manifest in BiSlider.jar identifies it as version 1.4.1, build
build0000482, built on 17-December-2006.

The aim of the present project is to (a) get the sources from Vernier's
build0000482 into GitHub, and (b) minimally reorganize those sources to
conform to current Maven conventions.

In Vernier's project, an ant build resulted in a jar that was both a
library and an executable jar, executed as:

```java -jar BiSlider.jar```

When executed this way, the jar manifest identified the main class as:

```com.visutools.nav.bislider.Test```

In the present project, the test code is executed using Maven:

```mvn exec:java -Dexec.mainClass="com.visutools.nav.bislider.Test" -Dexec.classpathScope="test"```

In the present project, the test code is in the src/test directory and it
is  _not_ incorporated into the project's main artifact, bislider-1.4.1.jar.

Additionally, the the HTML applet code has been moved to src/applet, and
no attempt has been made to utilize it.

Since one goal of this project is minimal alteration of the Java sources, and 
indeed, none of the source files have been altered in any way, the version
number remains at the version number designated by Vernier, 1.4.1. Beware that
earlier distributions by Vernier with the same version number may exist.

Copyright Notice
----------------

The following copyright information is reproduced verbatim from [Vernier's
BiSlider website](https://perso.limsi.fr/vernier/BiSlider/):

Copyright 1997-2005 Frederic Vernier.  All Rights Reserved.

Permission to use, copy, modify and distribute this software and its documentation for educational,
research and non-profit purposes, without fee, and without a written agreement is hereby granted,
provided that the above copyright notice and the following three paragraphs appear in all copies.

To request Permission to incorporate this software into commercial products contact
Frederic Vernier, 19 butte aux cailles street, Paris, 75013, France. Tel: (+33) 871 747 387.
eMail: Frederic.Vernier@laposte.net / Web site: http://www.limsi.fr/vernier/Individu/vernier

IN NO EVENT SHALL FREDERIC VERNIER BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL,
OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS
DOCUMENTATION, EVEN IF FREDERIC VERNIER HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.

FREDERIC VERNIER SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HERE UNDER
IS ON AN "AS IS" BASIS, AND FREDERIC VERNIER HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

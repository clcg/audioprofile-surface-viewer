Audiogram Surface Viewer
===================

Required Libraries
---------------------

The required lib

*	[Jzy3D](http://www.jzy3d.org/) (Included .91)
	+	jyz3d-api-&lt;version&gt;.jar
	+	jyz3d-swt-&lt;version&gt;.jar
*	[JOGL and GlueGen](http://www.jzy3d.org/) (Included 2.1.5)
	+	gluegen-rt-natives-macosx-universal.jar
	+	gluegen-rt-natives-windows-amd64.jar
	+	gluegen-rt-natives-windows-i586.jar
	+	gluegen-rt-natives-linux-i586.jar
	+	gluegen-rt-natives-linux-amd64.jar
	+	gluegen-rt.jar
	+	gluegen.jar
	+	jogl-all-natives-macosx-universal.jar
	+	jogl-all-natives-windows-amd64.jar
	+	jogl-all-natives-windows-i586.jar
	+	jogl-all-natives-linux-amd64.jar
	+	jogl-all-natives-linux-i586.jar
	+	jogl-all.jar
*	[Apache log4j](http://logging.apache.org/log4j/1.2/download.html) (Included 1.2.17)
	+	log4j-&lt;version&gt;.jar

Surface File Format
-------------------
#### Spec

<pre>
Lines:
1. Title that's displayed in the dropdown
2. Polynomial: (poly33,poly23,poly22)
3. Minimum age
4. Maximum age
5. Equation
6. Coefficient Names (tab separated)
7. Coefficient 
</pre>

#### Example
<pre>
DFNA8/12
poly33
1.000000
60.500000
p00 + p10*x + p01*y + p20*x^2 + p11*x*y + p02*y^2 + p30*x^3 + p21*x^2*y + p12*x*y^2 + p03*y^3
p00	p10	p01	p20	p11	p02	p30	p21	p12	p03
2.7823	18.098	-1.0388	-1.8884	-0.16325	0.060921	0.028148	0.015679	0.00083712	-0.00071394
</pre>


License
---------------------

Copyright (c) 2014, Kyle Taylor
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

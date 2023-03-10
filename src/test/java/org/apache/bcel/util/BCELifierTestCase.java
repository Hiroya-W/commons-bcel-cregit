begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|util
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertNotNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|AbstractTestCase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|HelloWorldCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|JavaClass
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Utility
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
operator|.
name|BinaryOpCreator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|provider
operator|.
name|ValueSource
import|;
end_import

begin_class
specifier|public
class|class
name|BCELifierTestCase
extends|extends
name|AbstractTestCase
block|{
specifier|private
specifier|static
specifier|final
name|String
name|EOL
init|=
name|System
operator|.
name|lineSeparator
argument_list|()
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CLASSPATH
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.class.path"
argument_list|)
operator|+
name|File
operator|.
name|pathSeparator
operator|+
literal|"."
decl_stmt|;
comment|// Canonicalise the javap output so it compares better
specifier|private
name|String
name|canonHashRef
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|input
operator|=
name|input
operator|.
name|replaceAll
argument_list|(
literal|"#\\d+"
argument_list|,
literal|"#n"
argument_list|)
expr_stmt|;
comment|// numbers may vary in length
name|input
operator|=
name|input
operator|.
name|replaceAll
argument_list|(
literal|" +"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
comment|// collapse spaces
name|input
operator|=
name|input
operator|.
name|replaceAll
argument_list|(
literal|"//.+"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// comments may vary
return|return
name|input
return|;
block|}
specifier|private
name|String
name|exec
parameter_list|(
specifier|final
name|File
name|workDir
parameter_list|,
specifier|final
name|String
modifier|...
name|args
parameter_list|)
throws|throws
name|Exception
block|{
comment|// System.err.println(java.util.Arrays.toString(args));
specifier|final
name|ProcessBuilder
name|pb
init|=
operator|new
name|ProcessBuilder
argument_list|(
name|args
argument_list|)
decl_stmt|;
name|pb
operator|.
name|directory
argument_list|(
name|workDir
argument_list|)
expr_stmt|;
name|pb
operator|.
name|redirectErrorStream
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|Process
name|proc
init|=
name|pb
operator|.
name|start
argument_list|()
decl_stmt|;
try|try
init|(
name|BufferedInputStream
name|is
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|proc
operator|.
name|getInputStream
argument_list|()
argument_list|)
init|)
block|{
specifier|final
name|byte
index|[]
name|buff
init|=
operator|new
name|byte
index|[
literal|2048
index|]
decl_stmt|;
name|int
name|len
decl_stmt|;
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|len
operator|=
name|is
operator|.
name|read
argument_list|(
name|buff
argument_list|)
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|new
name|String
argument_list|(
name|buff
argument_list|,
literal|0
argument_list|,
name|len
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|final
name|String
name|output
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|proc
operator|.
name|waitFor
argument_list|()
argument_list|,
name|output
argument_list|)
expr_stmt|;
return|return
name|output
return|;
block|}
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|ValueSource
argument_list|(
name|strings
operator|=
block|{
comment|// @formatter:off
literal|"iadd 3 2 = 5"
block|,
literal|"isub 3 2 = 1"
block|,
literal|"imul 3 2 = 6"
block|,
literal|"idiv 3 2 = 1"
block|,
literal|"irem 3 2 = 1"
block|,
literal|"iand 3 2 = 2"
block|,
literal|"ior 3 2 = 3"
block|,
literal|"ixor 3 2 = 1"
block|,
literal|"ishl 4 1 = 8"
block|,
literal|"ishr 4 1 = 2"
block|,
literal|"iushr 4 1 = 2"
block|,
literal|"ladd 3 2 = 5"
block|,
literal|"lsub 3 2 = 1"
block|,
literal|"lmul 3 2 = 6"
block|,
literal|"ldiv 3 2 = 1"
block|,
literal|"lrem 3 2 = 1"
block|,
literal|"land 3 2 = 2"
block|,
literal|"lor 3 2 = 3"
block|,
literal|"lxor 3 2 = 1"
block|,
literal|"lshl 4 1 = 8"
block|,
literal|"lshr 4 1 = 2"
block|,
literal|"lushr 4 1 = 2"
block|,
literal|"fadd 3 2 = 5.0"
block|,
literal|"fsub 3 2 = 1.0"
block|,
literal|"fmul 3 2 = 6.0"
block|,
literal|"fdiv 3 2 = 1.5"
block|,
literal|"frem 3 2 = 1.0"
block|,
literal|"dadd 3 2 = 5.0"
block|,
literal|"dsub 3 2 = 1.0"
block|,
literal|"dmul 3 2 = 6.0"
block|,
literal|"ddiv 3 2 = 1.5"
block|,
literal|"drem 3 2 = 1.0"
comment|// @formatter:on
block|}
argument_list|)
specifier|public
name|void
name|testBinaryOp
parameter_list|(
specifier|final
name|String
name|exp
parameter_list|)
throws|throws
name|Exception
block|{
name|BinaryOpCreator
operator|.
name|main
argument_list|(
operator|new
name|String
index|[]
block|{}
argument_list|)
expr_stmt|;
specifier|final
name|File
name|workDir
init|=
operator|new
name|File
argument_list|(
literal|"target"
argument_list|)
decl_stmt|;
specifier|final
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([a-z]{3,5}) ([-+]?\\d*\\.?\\d+) ([-+]?\\d*\\.?\\d+) = ([-+]?\\d*\\.?\\d+)"
argument_list|)
decl_stmt|;
specifier|final
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|exp
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcher
operator|.
name|matches
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|String
name|op
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
specifier|final
name|String
name|a
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
specifier|final
name|String
name|b
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
decl_stmt|;
specifier|final
name|String
name|expected
init|=
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
decl_stmt|;
specifier|final
name|String
name|javaAgent
init|=
name|getJavaAgent
argument_list|()
decl_stmt|;
if|if
condition|(
name|javaAgent
operator|==
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
name|expected
operator|+
name|EOL
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.generic.BinaryOp"
argument_list|,
name|op
argument_list|,
name|a
argument_list|,
name|b
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|String
name|runtimeExecJavaAgent
init|=
name|javaAgent
operator|.
name|replace
argument_list|(
literal|"jacoco.exec"
argument_list|,
literal|"jacoco_org.apache.bcel.generic.BinaryOp.exec"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
operator|+
name|EOL
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
name|runtimeExecJavaAgent
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.generic.BinaryOp"
argument_list|,
name|op
argument_list|,
name|a
argument_list|,
name|b
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|testClassOnPath
parameter_list|(
specifier|final
name|String
name|javaClassFileName
parameter_list|)
throws|throws
name|Exception
block|{
specifier|final
name|File
name|workDir
init|=
operator|new
name|File
argument_list|(
literal|"target"
argument_list|)
decl_stmt|;
specifier|final
name|File
name|infile
init|=
operator|new
name|File
argument_list|(
name|javaClassFileName
argument_list|)
decl_stmt|;
specifier|final
name|JavaClass
name|javaClass
init|=
name|BCELifier
operator|.
name|getJavaClass
argument_list|(
name|infile
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
name|JavaClass
operator|.
name|EXTENSION
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|javaClass
argument_list|)
expr_stmt|;
comment|// Get javap of the input class
specifier|final
name|String
name|initial
init|=
name|exec
argument_list|(
literal|null
argument_list|,
literal|"javap"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"-p"
argument_list|,
literal|"-c"
argument_list|,
name|javaClass
operator|.
name|getClassName
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|outFileName
init|=
name|javaClass
operator|.
name|getSourceFilePath
argument_list|()
operator|.
name|replace
argument_list|(
literal|".java"
argument_list|,
literal|"Creator.java"
argument_list|)
decl_stmt|;
specifier|final
name|File
name|outfile
init|=
operator|new
name|File
argument_list|(
name|workDir
argument_list|,
name|outFileName
argument_list|)
decl_stmt|;
name|Files
operator|.
name|createDirectories
argument_list|(
name|outfile
operator|.
name|getParentFile
argument_list|()
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|String
name|javaAgent
init|=
name|getJavaAgent
argument_list|()
decl_stmt|;
name|String
name|creatorSourceContents
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|javaAgent
operator|==
literal|null
condition|)
block|{
name|creatorSourceContents
operator|=
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.util.BCELifier"
argument_list|,
name|javaClass
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|String
name|runtimeExecJavaAgent
init|=
name|javaAgent
operator|.
name|replace
argument_list|(
literal|"jacoco.exec"
argument_list|,
literal|"jacoco_"
operator|+
name|infile
operator|.
name|getName
argument_list|()
operator|+
literal|".exec"
argument_list|)
decl_stmt|;
name|creatorSourceContents
operator|=
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
name|runtimeExecJavaAgent
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.util.BCELifier"
argument_list|,
name|javaClass
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Files
operator|.
name|write
argument_list|(
name|outfile
operator|.
name|toPath
argument_list|()
argument_list|,
name|creatorSourceContents
operator|.
name|getBytes
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"javac"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
name|outFileName
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|javaAgent
operator|==
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
name|javaClass
operator|.
name|getClassName
argument_list|()
operator|+
literal|"Creator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|String
name|runtimeExecJavaAgent
init|=
name|javaAgent
operator|.
name|replace
argument_list|(
literal|"jacoco.exec"
argument_list|,
literal|"jacoco_"
operator|+
name|Utility
operator|.
name|pathToPackage
argument_list|(
name|outFileName
argument_list|)
operator|+
literal|".exec"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
name|runtimeExecJavaAgent
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
name|javaClass
operator|.
name|getClassName
argument_list|()
operator|+
literal|"Creator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|final
name|String
name|output
init|=
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"javap"
argument_list|,
literal|"-p"
argument_list|,
literal|"-c"
argument_list|,
name|infile
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|canonHashRef
argument_list|(
name|initial
argument_list|)
argument_list|,
name|canonHashRef
argument_list|(
name|output
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testHelloWorld
parameter_list|()
throws|throws
name|Exception
block|{
name|HelloWorldCreator
operator|.
name|main
argument_list|(
operator|new
name|String
index|[]
block|{}
argument_list|)
expr_stmt|;
specifier|final
name|File
name|workDir
init|=
operator|new
name|File
argument_list|(
literal|"target"
argument_list|)
decl_stmt|;
specifier|final
name|String
name|javaAgent
init|=
name|getJavaAgent
argument_list|()
decl_stmt|;
if|if
condition|(
name|javaAgent
operator|==
literal|null
condition|)
block|{
name|assertEquals
argument_list|(
literal|"Hello World!"
operator|+
name|EOL
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.HelloWorld"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
specifier|final
name|String
name|runtimeExecJavaAgent
init|=
name|javaAgent
operator|.
name|replace
argument_list|(
literal|"jacoco.exec"
argument_list|,
literal|"jacoco_org.apache.bcel.HelloWorld.exec"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Hello World!"
operator|+
name|EOL
argument_list|,
name|exec
argument_list|(
name|workDir
argument_list|,
literal|"java"
argument_list|,
name|runtimeExecJavaAgent
argument_list|,
literal|"-cp"
argument_list|,
name|CLASSPATH
argument_list|,
literal|"org.apache.bcel.HelloWorld"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/*      * Dump a class using "javap" and compare with the same class recreated using BCELifier, "javac", "java" and dumped with      * "javap" TODO: detect if JDK present and skip test if not      */
annotation|@
name|ParameterizedTest
annotation|@
name|ValueSource
argument_list|(
name|strings
operator|=
block|{
comment|// @formatter:off
literal|"org.apache.commons.lang.math.Fraction.class"
block|,
literal|"org.apache.commons.lang.exception.NestableDelegate.class"
block|,
literal|"org.apache.commons.lang.builder.CompareToBuilder.class"
block|,
literal|"org.apache.commons.lang.builder.ToStringBuilder.class"
block|,
literal|"org.apache.commons.lang.SerializationUtils.class"
block|,
literal|"org.apache.commons.lang.ArrayUtils.class"
block|,
literal|"target/test-classes/Java8Example.class"
block|,
literal|"target/test-classes/Java8Example2.class"
block|,
literal|"target/test-classes/Java4Example.class"
comment|// @formatter:on
block|}
argument_list|)
specifier|public
name|void
name|testJavapCompare
parameter_list|(
specifier|final
name|String
name|pathToClass
parameter_list|)
throws|throws
name|Exception
block|{
name|testClassOnPath
argument_list|(
name|pathToClass
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testMainNoArg
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|PrintStream
name|sysout
init|=
name|System
operator|.
name|out
decl_stmt|;
try|try
block|{
specifier|final
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|System
operator|.
name|setOut
argument_list|(
operator|new
name|PrintStream
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|BCELifier
operator|.
name|main
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
specifier|final
name|String
name|outputNoArgs
init|=
operator|new
name|String
argument_list|(
name|out
operator|.
name|toByteArray
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Usage: BCELifier className"
operator|+
name|EOL
operator|+
literal|"\tThe class must exist on the classpath"
operator|+
name|EOL
argument_list|,
name|outputNoArgs
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|System
operator|.
name|setOut
argument_list|(
name|sysout
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testStart
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|OutputStream
name|os
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
specifier|final
name|JavaClass
name|javaClass
init|=
name|BCELifier
operator|.
name|getJavaClass
argument_list|(
literal|"Java8Example"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|javaClass
argument_list|)
expr_stmt|;
specifier|final
name|BCELifier
name|bcelifier
init|=
operator|new
name|BCELifier
argument_list|(
name|javaClass
argument_list|,
name|os
argument_list|)
decl_stmt|;
name|bcelifier
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit


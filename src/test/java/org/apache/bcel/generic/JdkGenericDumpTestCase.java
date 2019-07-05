begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *   http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
package|;
end_package

begin_import
import|import static
name|com
operator|.
name|sun
operator|.
name|jna
operator|.
name|platform
operator|.
name|win32
operator|.
name|WinReg
operator|.
name|HKEY_LOCAL_MACHINE
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertArrayEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|fail
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
name|FileFilter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|FileSystems
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
name|FileVisitResult
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
name|nio
operator|.
name|file
operator|.
name|Path
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
name|PathMatcher
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
name|SimpleFileVisitor
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
name|attribute
operator|.
name|BasicFileAttributes
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|jar
operator|.
name|JarEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|jar
operator|.
name|JarFile
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
name|ClassParser
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
name|Code
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
name|Method
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
name|util
operator|.
name|ModularRuntimeImage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|JavaVersion
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|StringUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|SystemUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assume
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
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
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|jna
operator|.
name|platform
operator|.
name|win32
operator|.
name|Advapi32Util
import|;
end_import

begin_comment
comment|/**  * Test that the generic dump() methods work on the JDK classes Reads each class into an instruction list and then dumps  * the instructions. The output bytes should be the same as the input.  *<p>  * Set the property {@value #EXTRA_JAVA_HOMES} to a comma-sepaarted list of JRE/JDK paths for additional testing.  *</p>  */
end_comment

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
specifier|public
class|class
name|JdkGenericDumpTestCase
block|{
specifier|private
specifier|static
specifier|final
name|String
name|EXTRA_JAVA_HOMES
init|=
literal|"ExtraJavaHomes"
decl_stmt|;
specifier|private
specifier|static
class|class
name|ClassParserFilesVisitor
extends|extends
name|SimpleFileVisitor
argument_list|<
name|Path
argument_list|>
block|{
specifier|private
specifier|final
name|PathMatcher
name|matcher
decl_stmt|;
name|ClassParserFilesVisitor
parameter_list|(
specifier|final
name|String
name|pattern
parameter_list|)
block|{
name|matcher
operator|=
name|FileSystems
operator|.
name|getDefault
argument_list|()
operator|.
name|getPathMatcher
argument_list|(
literal|"glob:"
operator|+
name|pattern
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|find
parameter_list|(
specifier|final
name|Path
name|path
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|Path
name|name
init|=
name|path
operator|.
name|getFileName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
operator|&&
name|matcher
operator|.
name|matches
argument_list|(
name|name
argument_list|)
condition|)
block|{
try|try
init|(
specifier|final
name|InputStream
name|inputStream
init|=
name|Files
operator|.
name|newInputStream
argument_list|(
name|path
argument_list|)
init|)
block|{
specifier|final
name|ClassParser
name|parser
init|=
operator|new
name|ClassParser
argument_list|(
name|inputStream
argument_list|,
name|name
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|JavaClass
name|jc
init|=
name|parser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|jc
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|FileVisitResult
name|preVisitDirectory
parameter_list|(
specifier|final
name|Path
name|dir
parameter_list|,
specifier|final
name|BasicFileAttributes
name|attrs
parameter_list|)
throws|throws
name|IOException
block|{
name|find
argument_list|(
name|dir
argument_list|)
expr_stmt|;
return|return
name|FileVisitResult
operator|.
name|CONTINUE
return|;
block|}
annotation|@
name|Override
specifier|public
name|FileVisitResult
name|visitFile
parameter_list|(
specifier|final
name|Path
name|file
parameter_list|,
specifier|final
name|BasicFileAttributes
name|attrs
parameter_list|)
throws|throws
name|IOException
block|{
name|find
argument_list|(
name|file
argument_list|)
expr_stmt|;
return|return
name|FileVisitResult
operator|.
name|CONTINUE
return|;
block|}
annotation|@
name|Override
specifier|public
name|FileVisitResult
name|visitFileFailed
parameter_list|(
specifier|final
name|Path
name|file
parameter_list|,
specifier|final
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return
name|FileVisitResult
operator|.
name|CONTINUE
return|;
block|}
block|}
specifier|private
specifier|static
specifier|final
name|char
index|[]
name|hexArray
init|=
literal|"0123456789ABCDEF"
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|KEY_JDK
init|=
literal|"SOFTWARE\\JavaSoft\\Java Development Kit"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|KEY_JDK_9
init|=
literal|"SOFTWARE\\JavaSoft\\JDK"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|KEY_JRE
init|=
literal|"SOFTWARE\\JavaSoft\\Java Runtime Environment"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|KEY_JRE_9
init|=
literal|"SOFTWARE\\JavaSoft\\JRE"
decl_stmt|;
specifier|private
specifier|static
name|void
name|addAllJavaHomesOnWindows
parameter_list|(
specifier|final
name|String
name|keyJre
parameter_list|,
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|javaHomes
parameter_list|)
block|{
if|if
condition|(
name|Advapi32Util
operator|.
name|registryKeyExists
argument_list|(
name|HKEY_LOCAL_MACHINE
argument_list|,
name|keyJre
argument_list|)
condition|)
block|{
name|javaHomes
operator|.
name|addAll
argument_list|(
name|findJavaHomesOnWindows
argument_list|(
name|keyJre
argument_list|,
name|Advapi32Util
operator|.
name|registryGetKeys
argument_list|(
name|HKEY_LOCAL_MACHINE
argument_list|,
name|keyJre
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|String
name|bytesToHex
parameter_list|(
specifier|final
name|byte
index|[]
name|bytes
parameter_list|)
block|{
specifier|final
name|char
index|[]
name|hexChars
init|=
operator|new
name|char
index|[
name|bytes
operator|.
name|length
operator|*
literal|3
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
specifier|final
name|byte
name|b
range|:
name|bytes
control|)
block|{
specifier|final
name|int
name|v
init|=
name|b
operator|&
literal|0xFF
decl_stmt|;
name|hexChars
index|[
name|i
operator|++
index|]
operator|=
name|hexArray
index|[
name|v
operator|>>>
literal|4
index|]
expr_stmt|;
name|hexChars
index|[
name|i
operator|++
index|]
operator|=
name|hexArray
index|[
name|v
operator|&
literal|0x0F
index|]
expr_stmt|;
name|hexChars
index|[
name|i
operator|++
index|]
operator|=
literal|' '
expr_stmt|;
block|}
return|return
operator|new
name|String
argument_list|(
name|hexChars
argument_list|)
return|;
block|}
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{0}"
argument_list|)
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|data
parameter_list|()
block|{
return|return
name|findJavaHomes
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findJavaHomes
parameter_list|()
block|{
if|if
condition|(
name|SystemUtils
operator|.
name|IS_OS_WINDOWS
condition|)
block|{
return|return
name|findJavaHomesOnWindows
argument_list|()
return|;
block|}
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|javaHomes
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|javaHomes
operator|.
name|add
argument_list|(
name|SystemUtils
operator|.
name|JAVA_HOME
argument_list|)
expr_stmt|;
return|return
name|javaHomes
return|;
block|}
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findJavaHomesOnWindows
parameter_list|()
block|{
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|javaHomes
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|addAllJavaHomesOnWindows
argument_list|(
name|KEY_JRE
argument_list|,
name|javaHomes
argument_list|)
expr_stmt|;
name|addAllJavaHomesOnWindows
argument_list|(
name|KEY_JRE_9
argument_list|,
name|javaHomes
argument_list|)
expr_stmt|;
name|addAllJavaHomesOnWindows
argument_list|(
name|KEY_JDK
argument_list|,
name|javaHomes
argument_list|)
expr_stmt|;
name|addAllJavaHomesOnWindows
argument_list|(
name|KEY_JDK_9
argument_list|,
name|javaHomes
argument_list|)
expr_stmt|;
name|addAllJavaHomes
argument_list|(
name|EXTRA_JAVA_HOMES
argument_list|,
name|javaHomes
argument_list|)
expr_stmt|;
return|return
name|javaHomes
return|;
block|}
specifier|private
specifier|static
name|void
name|addAllJavaHomes
parameter_list|(
name|String
name|extraJavaHomesProp
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|javaHomes
parameter_list|)
block|{
name|String
name|path
init|=
name|System
operator|.
name|getProperty
argument_list|(
name|extraJavaHomesProp
argument_list|)
decl_stmt|;
if|if
condition|(
name|StringUtils
operator|.
name|isEmpty
argument_list|(
name|path
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
index|[]
name|paths
init|=
name|path
operator|.
name|split
argument_list|(
name|File
operator|.
name|pathSeparator
argument_list|)
decl_stmt|;
name|javaHomes
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|paths
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findJavaHomesOnWindows
parameter_list|(
specifier|final
name|String
name|keyJavaHome
parameter_list|,
specifier|final
name|String
index|[]
name|keys
parameter_list|)
block|{
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|javaHomes
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|keys
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|key
range|:
name|keys
control|)
block|{
if|if
condition|(
name|Advapi32Util
operator|.
name|registryKeyExists
argument_list|(
name|HKEY_LOCAL_MACHINE
argument_list|,
name|keyJavaHome
operator|+
literal|"\\"
operator|+
name|key
argument_list|)
condition|)
block|{
specifier|final
name|String
name|javaHome
init|=
name|Advapi32Util
operator|.
name|registryGetStringValue
argument_list|(
name|HKEY_LOCAL_MACHINE
argument_list|,
name|keyJavaHome
operator|+
literal|"\\"
operator|+
name|key
argument_list|,
literal|"JavaHome"
argument_list|)
decl_stmt|;
if|if
condition|(
name|StringUtils
operator|.
name|isNoneBlank
argument_list|(
name|javaHome
argument_list|)
condition|)
block|{
if|if
condition|(
operator|new
name|File
argument_list|(
name|javaHome
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|javaHomes
operator|.
name|add
argument_list|(
name|javaHome
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|javaHomes
return|;
block|}
specifier|private
specifier|final
name|String
name|javaHome
decl_stmt|;
specifier|public
name|JdkGenericDumpTestCase
parameter_list|(
specifier|final
name|String
name|javaHome
parameter_list|)
block|{
name|this
operator|.
name|javaHome
operator|=
name|javaHome
expr_stmt|;
block|}
specifier|private
name|void
name|compare
parameter_list|(
specifier|final
name|String
name|name
parameter_list|,
specifier|final
name|Method
name|m
parameter_list|)
block|{
comment|// System.out.println("Method: " + m);
specifier|final
name|Code
name|c
init|=
name|m
operator|.
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
block|{
return|return;
comment|// e.g. abstract method
block|}
specifier|final
name|byte
index|[]
name|src
init|=
name|c
operator|.
name|getCode
argument_list|()
decl_stmt|;
specifier|final
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|(
name|src
argument_list|)
decl_stmt|;
specifier|final
name|byte
index|[]
name|out
init|=
name|il
operator|.
name|getByteCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|src
operator|.
name|length
operator|==
name|out
operator|.
name|length
condition|)
block|{
name|assertArrayEquals
argument_list|(
name|name
operator|+
literal|": "
operator|+
name|m
operator|.
name|toString
argument_list|()
argument_list|,
name|src
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|name
operator|+
literal|": "
operator|+
name|m
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|src
operator|.
name|length
operator|+
literal|" "
operator|+
name|out
operator|.
name|length
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|bytesToHex
argument_list|(
name|src
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|bytesToHex
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|InstructionHandle
name|ih
range|:
name|il
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|ih
operator|.
name|toString
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|fail
argument_list|(
literal|"Array comparison failure"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|File
index|[]
name|listJdkJars
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|File
name|javaLib
init|=
operator|new
name|File
argument_list|(
name|javaHome
argument_list|,
literal|"lib"
argument_list|)
decl_stmt|;
return|return
name|javaLib
operator|.
name|listFiles
argument_list|(
operator|new
name|FileFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
specifier|final
name|File
name|file
parameter_list|)
block|{
return|return
name|file
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".jar"
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
specifier|private
name|File
index|[]
name|listJdkModules
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|File
name|javaLib
init|=
operator|new
name|File
argument_list|(
name|javaHome
argument_list|,
literal|"jmods"
argument_list|)
decl_stmt|;
return|return
name|javaLib
operator|.
name|listFiles
argument_list|(
operator|new
name|FileFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
specifier|final
name|File
name|file
parameter_list|)
block|{
return|return
name|file
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".jmod"
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
specifier|private
name|void
name|testJar
parameter_list|(
specifier|final
name|File
name|file
parameter_list|)
throws|throws
name|Exception
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|file
argument_list|)
expr_stmt|;
try|try
init|(
name|JarFile
name|jar
init|=
operator|new
name|JarFile
argument_list|(
name|file
argument_list|)
init|)
block|{
specifier|final
name|Enumeration
argument_list|<
name|JarEntry
argument_list|>
name|en
init|=
name|jar
operator|.
name|entries
argument_list|()
decl_stmt|;
while|while
condition|(
name|en
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
specifier|final
name|JarEntry
name|e
init|=
name|en
operator|.
name|nextElement
argument_list|()
decl_stmt|;
specifier|final
name|String
name|name
init|=
name|e
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|endsWith
argument_list|(
literal|".class"
argument_list|)
condition|)
block|{
comment|// System.out.println("- " + name);
try|try
init|(
name|InputStream
name|in
init|=
name|jar
operator|.
name|getInputStream
argument_list|(
name|e
argument_list|)
init|)
block|{
specifier|final
name|ClassParser
name|parser
init|=
operator|new
name|ClassParser
argument_list|(
name|in
argument_list|,
name|name
argument_list|)
decl_stmt|;
specifier|final
name|JavaClass
name|jc
init|=
name|parser
operator|.
name|parse
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|Method
name|m
range|:
name|jc
operator|.
name|getMethods
argument_list|()
control|)
block|{
name|compare
argument_list|(
name|name
argument_list|,
name|m
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testJdkJars
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|File
index|[]
name|jars
init|=
name|listJdkJars
argument_list|()
decl_stmt|;
if|if
condition|(
name|jars
operator|!=
literal|null
condition|)
block|{
for|for
control|(
specifier|final
name|File
name|file
range|:
name|jars
control|)
block|{
name|testJar
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testJdkModules
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|File
index|[]
name|jmods
init|=
name|listJdkModules
argument_list|()
decl_stmt|;
if|if
condition|(
name|jmods
operator|!=
literal|null
condition|)
block|{
for|for
control|(
specifier|final
name|File
name|file
range|:
name|jmods
control|)
block|{
name|testJar
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
specifier|public
name|void
name|testJreModules
parameter_list|()
throws|throws
name|Exception
block|{
name|Assume
operator|.
name|assumeTrue
argument_list|(
name|SystemUtils
operator|.
name|isJavaVersionAtLeast
argument_list|(
name|JavaVersion
operator|.
name|JAVA_9
argument_list|)
argument_list|)
expr_stmt|;
try|try
init|(
specifier|final
name|ModularRuntimeImage
name|mri
init|=
operator|new
name|ModularRuntimeImage
argument_list|(
name|javaHome
argument_list|)
init|)
block|{
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|modules
init|=
name|mri
operator|.
name|modules
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|modules
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|Path
name|path
range|:
name|modules
control|)
block|{
name|Files
operator|.
name|walkFileTree
argument_list|(
name|path
argument_list|,
operator|new
name|ClassParserFilesVisitor
argument_list|(
literal|"*.class"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit


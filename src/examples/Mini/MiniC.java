begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|Mini
package|;
end_package

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
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|generic
operator|.
name|ClassGen
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
name|ConstantPoolGen
import|;
end_import

begin_class
specifier|public
class|class
name|MiniC
implements|implements
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
block|{
specifier|private
specifier|static
name|Vector
argument_list|<
name|String
argument_list|>
name|errors
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|Vector
argument_list|<
name|String
argument_list|>
name|warnings
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|String
name|file
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|int
name|pass
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|argv
parameter_list|)
block|{
specifier|final
name|String
index|[]
name|file_name
init|=
operator|new
name|String
index|[
name|argv
operator|.
name|length
index|]
decl_stmt|;
name|int
name|files
init|=
literal|0
decl_stmt|;
name|MiniParser
name|parser
init|=
literal|null
decl_stmt|;
name|String
name|base_name
init|=
literal|null
decl_stmt|;
name|boolean
name|byte_code
init|=
literal|true
decl_stmt|;
try|try
block|{
comment|/* Parse command line arguments.        */
for|for
control|(
specifier|final
name|String
name|element
range|:
name|argv
control|)
block|{
if|if
condition|(
name|element
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'-'
condition|)
block|{
comment|// command line switch
if|if
condition|(
name|element
operator|.
name|equals
argument_list|(
literal|"-java"
argument_list|)
condition|)
block|{
name|byte_code
operator|=
literal|false
expr_stmt|;
block|}
if|else if
condition|(
name|element
operator|.
name|equals
argument_list|(
literal|"-bytecode"
argument_list|)
condition|)
block|{
name|byte_code
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown option: "
operator|+
name|element
argument_list|)
throw|;
block|}
block|}
else|else
block|{
comment|// add file name to list
name|file_name
index|[
name|files
operator|++
index|]
operator|=
name|element
expr_stmt|;
block|}
block|}
if|if
condition|(
name|files
operator|==
literal|0
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Nothing to compile."
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|files
condition|;
name|j
operator|++
control|)
block|{
name|errors
operator|=
operator|new
name|Vector
argument_list|<>
argument_list|()
expr_stmt|;
name|warnings
operator|=
operator|new
name|Vector
argument_list|<>
argument_list|()
expr_stmt|;
name|pass
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|j
operator|==
literal|0
condition|)
block|{
name|parser
operator|=
operator|new
name|MiniParser
argument_list|(
operator|new
name|java
operator|.
name|io
operator|.
name|FileInputStream
argument_list|(
name|file_name
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|MiniParser
operator|.
name|ReInit
argument_list|(
operator|new
name|java
operator|.
name|io
operator|.
name|FileInputStream
argument_list|(
name|file_name
index|[
name|j
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|int
name|index
init|=
name|file_name
index|[
name|j
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|base_name
operator|=
name|file_name
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|base_name
operator|=
name|file_name
index|[
name|j
index|]
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|index
operator|=
name|base_name
operator|.
name|lastIndexOf
argument_list|(
name|File
operator|.
name|separatorChar
argument_list|)
operator|)
operator|>
literal|0
condition|)
block|{
name|base_name
operator|=
name|base_name
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|file
operator|=
name|file_name
index|[
name|j
index|]
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Parsing ..."
argument_list|)
expr_stmt|;
name|MiniParser
operator|.
name|Program
argument_list|()
expr_stmt|;
name|ASTProgram
name|program
init|=
operator|(
name|ASTProgram
operator|)
name|MiniParser
operator|.
name|jjtree
operator|.
name|rootNode
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pass 1: Optimizing parse tree ..."
argument_list|)
expr_stmt|;
name|pass
operator|=
literal|1
expr_stmt|;
name|program
operator|=
name|program
operator|.
name|traverse
argument_list|()
expr_stmt|;
comment|// program.dump(">");
if|if
condition|(
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pass 2: Type checking (I) ..."
argument_list|)
expr_stmt|;
name|program
operator|.
name|eval
argument_list|(
name|pass
operator|=
literal|2
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pass 3: Type checking (II) ..."
argument_list|)
expr_stmt|;
name|program
operator|.
name|eval
argument_list|(
name|pass
operator|=
literal|3
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|errors
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|errors
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|warnings
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|warnings
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|byte_code
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pass 5: Generating byte code ..."
argument_list|)
expr_stmt|;
specifier|final
name|ClassGen
name|class_gen
init|=
operator|new
name|ClassGen
argument_list|(
name|base_name
argument_list|,
literal|"java.lang.Object"
argument_list|,
name|file_name
index|[
name|j
index|]
argument_list|,
name|ACC_PUBLIC
operator||
name|ACC_FINAL
operator||
name|ACC_SUPER
argument_list|,
literal|null
argument_list|)
decl_stmt|;
specifier|final
name|ConstantPoolGen
name|cp
init|=
name|class_gen
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|program
operator|.
name|byte_code
argument_list|(
name|class_gen
argument_list|,
name|cp
argument_list|)
expr_stmt|;
specifier|final
name|JavaClass
name|clazz
init|=
name|class_gen
operator|.
name|getJavaClass
argument_list|()
decl_stmt|;
name|clazz
operator|.
name|dump
argument_list|(
name|base_name
operator|+
literal|".class"
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
literal|"Pass 5: Generating Java code ..."
argument_list|)
expr_stmt|;
specifier|final
name|PrintWriter
name|out
init|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|base_name
operator|+
literal|".java"
argument_list|)
argument_list|)
decl_stmt|;
name|program
operator|.
name|code
argument_list|(
name|out
argument_list|,
name|base_name
argument_list|)
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pass 6: Compiling Java code ..."
argument_list|)
expr_stmt|;
specifier|final
name|String
index|[]
name|args
init|=
block|{
literal|"javac"
block|,
name|base_name
operator|+
literal|".java"
block|}
decl_stmt|;
comment|//sun.tools.javac.Main compiler = new sun.tools.javac.Main(System.err, "javac");
try|try
block|{
specifier|final
name|Process
name|p
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|args
argument_list|)
decl_stmt|;
name|p
operator|.
name|waitFor
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
comment|//compiler.compile(args);
block|}
block|}
if|if
condition|(
operator|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
operator|||
operator|(
name|warnings
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|errors
operator|.
name|size
argument_list|()
operator|+
literal|" errors and "
operator|+
name|warnings
operator|.
name|size
argument_list|()
operator|+
literal|" warnings."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
specifier|final
specifier|static
name|void
name|addError
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|,
specifier|final
name|String
name|err
parameter_list|)
block|{
if|if
condition|(
name|pass
operator|!=
literal|2
condition|)
block|{
name|errors
operator|.
name|addElement
argument_list|(
name|file
operator|+
literal|":"
operator|+
name|fillup
argument_list|(
name|line
argument_list|,
literal|3
argument_list|)
operator|+
literal|","
operator|+
name|fillup
argument_list|(
name|column
argument_list|,
literal|2
argument_list|)
operator|+
literal|": "
operator|+
name|err
argument_list|)
expr_stmt|;
block|}
block|}
specifier|final
specifier|static
name|void
name|addWarning
parameter_list|(
specifier|final
name|int
name|line
parameter_list|,
specifier|final
name|int
name|column
parameter_list|,
specifier|final
name|String
name|err
parameter_list|)
block|{
name|warnings
operator|.
name|addElement
argument_list|(
literal|"Warning: "
operator|+
name|file
operator|+
literal|":"
operator|+
name|fillup
argument_list|(
name|line
argument_list|,
literal|3
argument_list|)
operator|+
literal|","
operator|+
name|fillup
argument_list|(
name|column
argument_list|,
literal|3
argument_list|)
operator|+
literal|": "
operator|+
name|err
argument_list|)
expr_stmt|;
block|}
specifier|final
specifier|static
name|String
name|fillup
parameter_list|(
specifier|final
name|int
name|n
parameter_list|,
specifier|final
name|int
name|len
parameter_list|)
block|{
specifier|final
name|String
name|str
init|=
name|Integer
operator|.
name|toString
argument_list|(
name|n
argument_list|)
decl_stmt|;
specifier|final
name|int
name|diff
init|=
name|len
operator|-
name|str
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|diff
operator|>
literal|0
condition|)
block|{
specifier|final
name|char
index|[]
name|chs
init|=
operator|new
name|char
index|[
name|diff
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|diff
condition|;
name|i
operator|++
control|)
block|{
name|chs
index|[
name|i
index|]
operator|=
literal|' '
expr_stmt|;
block|}
return|return
operator|new
name|String
argument_list|(
name|chs
argument_list|)
operator|+
name|str
return|;
block|}
else|else
block|{
return|return
name|str
return|;
block|}
block|}
specifier|final
specifier|static
name|void
name|addWarning
parameter_list|(
specifier|final
name|String
name|err
parameter_list|)
block|{
name|warnings
operator|.
name|addElement
argument_list|(
name|err
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit


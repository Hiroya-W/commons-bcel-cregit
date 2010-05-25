begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.   *  */
end_comment

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Constants
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
name|ConstantClass
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
name|ConstantPool
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
name|ConstantUtf8
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
name|ConstantPoolGen
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
name|GETSTATIC
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
name|INVOKESPECIAL
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
name|INVOKEVIRTUAL
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
name|InstructionHandle
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
name|InstructionList
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
name|MethodGen
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
name|PUSH
import|;
end_import

begin_comment
comment|/**  * Read class file(s) and patch all of its methods, so that they print  * "hello" and their name and signature before doing anything else.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|helloify
implements|implements
name|Constants
block|{
specifier|private
specifier|static
name|String
name|class_name
decl_stmt|;
specifier|private
specifier|static
name|ConstantPoolGen
name|cp
decl_stmt|;
specifier|private
specifier|static
name|int
name|out
decl_stmt|;
comment|// reference to System.out
specifier|private
specifier|static
name|int
name|println
decl_stmt|;
comment|// reference to PrintStream.println
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|argv
parameter_list|)
block|{
try|try
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|argv
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|argv
index|[
name|i
index|]
operator|.
name|endsWith
argument_list|(
literal|".class"
argument_list|)
condition|)
block|{
name|JavaClass
name|java_class
init|=
operator|new
name|ClassParser
argument_list|(
name|argv
index|[
name|i
index|]
argument_list|)
operator|.
name|parse
argument_list|()
decl_stmt|;
name|ConstantPool
name|constants
init|=
name|java_class
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|String
name|file_name
init|=
name|argv
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|argv
index|[
name|i
index|]
operator|.
name|length
argument_list|()
operator|-
literal|6
argument_list|)
operator|+
literal|"_hello.class"
decl_stmt|;
name|cp
operator|=
operator|new
name|ConstantPoolGen
argument_list|(
name|constants
argument_list|)
expr_stmt|;
name|helloifyClassName
argument_list|(
name|java_class
argument_list|)
expr_stmt|;
name|out
operator|=
name|cp
operator|.
name|addFieldref
argument_list|(
literal|"java.lang.System"
argument_list|,
literal|"out"
argument_list|,
literal|"Ljava/io/PrintStream;"
argument_list|)
expr_stmt|;
name|println
operator|=
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.PrintStream"
argument_list|,
literal|"println"
argument_list|,
literal|"(Ljava/lang/String;)V"
argument_list|)
expr_stmt|;
comment|/* Patch all methods. 	   */
name|Method
index|[]
name|methods
init|=
name|java_class
operator|.
name|getMethods
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|methods
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|methods
index|[
name|j
index|]
operator|=
name|helloifyMethod
argument_list|(
name|methods
index|[
name|j
index|]
argument_list|)
expr_stmt|;
block|}
comment|/* Finally dump it back to a file. 	   */
name|java_class
operator|.
name|setConstantPool
argument_list|(
name|cp
operator|.
name|getFinalConstantPool
argument_list|()
argument_list|)
expr_stmt|;
name|java_class
operator|.
name|dump
argument_list|(
name|file_name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
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
comment|/** Change class name to<old_name>_hello    */
specifier|private
specifier|static
name|void
name|helloifyClassName
parameter_list|(
name|JavaClass
name|java_class
parameter_list|)
block|{
name|class_name
operator|=
name|java_class
operator|.
name|getClassName
argument_list|()
operator|+
literal|"_hello"
expr_stmt|;
name|int
name|index
init|=
name|java_class
operator|.
name|getClassNameIndex
argument_list|()
decl_stmt|;
name|index
operator|=
operator|(
operator|(
name|ConstantClass
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|index
argument_list|)
operator|)
operator|.
name|getNameIndex
argument_list|()
expr_stmt|;
name|cp
operator|.
name|setConstant
argument_list|(
name|index
argument_list|,
operator|new
name|ConstantUtf8
argument_list|(
name|class_name
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**    * Patch a method.    */
specifier|private
specifier|static
name|Method
name|helloifyMethod
parameter_list|(
name|Method
name|m
parameter_list|)
block|{
name|Code
name|code
init|=
name|m
operator|.
name|getCode
argument_list|()
decl_stmt|;
name|int
name|flags
init|=
name|m
operator|.
name|getAccessFlags
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|m
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|// Sanity check
if|if
condition|(
name|m
operator|.
name|isNative
argument_list|()
operator|||
name|m
operator|.
name|isAbstract
argument_list|()
operator|||
operator|(
name|code
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|m
return|;
block|}
comment|/* Create instruction list to be inserted at method start.      */
name|String
name|mesg
init|=
literal|"Hello from "
operator|+
name|Utility
operator|.
name|methodSignatureToString
argument_list|(
name|m
operator|.
name|getSignature
argument_list|()
argument_list|,
name|name
argument_list|,
name|Utility
operator|.
name|accessToString
argument_list|(
name|flags
argument_list|)
argument_list|)
decl_stmt|;
name|InstructionList
name|patch
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
name|patch
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|patch
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
name|mesg
argument_list|)
argument_list|)
expr_stmt|;
name|patch
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|println
argument_list|)
argument_list|)
expr_stmt|;
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|m
argument_list|,
name|class_name
argument_list|,
name|cp
argument_list|)
decl_stmt|;
name|InstructionList
name|il
init|=
name|mg
operator|.
name|getInstructionList
argument_list|()
decl_stmt|;
name|InstructionHandle
index|[]
name|ihs
init|=
name|il
operator|.
name|getInstructionHandles
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"<init>"
argument_list|)
condition|)
block|{
comment|// First let the super or other constructor be called
for|for
control|(
name|int
name|j
init|=
literal|1
init|;
name|j
operator|<
name|ihs
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|ihs
index|[
name|j
index|]
operator|.
name|getInstruction
argument_list|()
operator|instanceof
name|INVOKESPECIAL
condition|)
block|{
name|il
operator|.
name|append
argument_list|(
name|ihs
index|[
name|j
index|]
argument_list|,
name|patch
argument_list|)
expr_stmt|;
comment|// Should check: method name == "<init>"
break|break;
block|}
block|}
block|}
else|else
block|{
name|il
operator|.
name|insert
argument_list|(
name|ihs
index|[
literal|0
index|]
argument_list|,
name|patch
argument_list|)
expr_stmt|;
block|}
comment|/* Stack size must be at least 2, since the println method takes 2 argument.      */
if|if
condition|(
name|code
operator|.
name|getMaxStack
argument_list|()
operator|<
literal|2
condition|)
block|{
name|mg
operator|.
name|setMaxStack
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
name|m
operator|=
name|mg
operator|.
name|getMethod
argument_list|()
expr_stmt|;
name|il
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// Reuse instruction handles
return|return
name|m
return|;
block|}
block|}
end_class

end_unit


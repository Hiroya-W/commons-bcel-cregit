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
name|Repository
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
name|MethodGen
import|;
end_import

begin_comment
comment|/**  * Read class file(s) and examine all of its methods, determining the  * maximum stack depth used by analyzing control flow.  *  * @version $Id$  * @author<A HREF="mailto:m.dahm@gmx.de">M. Dahm</A>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|maxstack
block|{
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
name|String
name|class_name
init|=
name|argv
index|[
name|i
index|]
decl_stmt|;
name|JavaClass
name|java_class
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|class_name
argument_list|)
decl_stmt|;
if|if
condition|(
name|java_class
operator|==
literal|null
condition|)
block|{
name|java_class
operator|=
operator|new
name|ClassParser
argument_list|(
name|class_name
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
block|}
name|ConstantPoolGen
name|cp
init|=
operator|new
name|ConstantPoolGen
argument_list|(
name|java_class
operator|.
name|getConstantPool
argument_list|()
argument_list|)
decl_stmt|;
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
name|Method
name|m
init|=
name|methods
index|[
name|j
index|]
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|m
operator|.
name|isAbstract
argument_list|()
operator|||
name|m
operator|.
name|isNative
argument_list|()
operator|)
condition|)
block|{
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|m
argument_list|,
name|argv
index|[
name|i
index|]
argument_list|,
name|cp
argument_list|)
decl_stmt|;
name|int
name|compiled_stack
init|=
name|mg
operator|.
name|getMaxStack
argument_list|()
decl_stmt|;
name|int
name|compiled_locals
init|=
name|mg
operator|.
name|getMaxLocals
argument_list|()
decl_stmt|;
name|mg
operator|.
name|setMaxStack
argument_list|()
expr_stmt|;
comment|// Recompute value
name|mg
operator|.
name|setMaxLocals
argument_list|()
expr_stmt|;
name|int
name|computed_stack
init|=
name|mg
operator|.
name|getMaxStack
argument_list|()
decl_stmt|;
name|int
name|computed_locals
init|=
name|mg
operator|.
name|getMaxLocals
argument_list|()
decl_stmt|;
name|mg
operator|.
name|getInstructionList
argument_list|()
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// Reuse instruction handles
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|m
argument_list|)
expr_stmt|;
if|if
condition|(
name|computed_stack
operator|==
name|compiled_stack
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Stack ok("
operator|+
name|computed_stack
operator|+
literal|")"
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
literal|"\nCompiled stack size "
operator|+
name|compiled_stack
operator|+
literal|" computed size "
operator|+
name|computed_stack
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|computed_locals
operator|==
name|compiled_locals
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Locals ok("
operator|+
name|computed_locals
operator|+
literal|")"
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
literal|"\nCompiled locals "
operator|+
name|compiled_locals
operator|+
literal|" computed size "
operator|+
name|computed_locals
argument_list|)
expr_stmt|;
block|}
block|}
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
block|}
end_class

end_unit


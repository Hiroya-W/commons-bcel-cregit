begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|verifier
operator|.
name|structurals
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
operator|.
name|CodeExceptionGen
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
name|MethodGen
import|;
end_import

begin_comment
comment|/**  * This class allows easy access to ExceptionHandler objects.  *  * @version $Id$  * @author Enver Haase  */
end_comment

begin_class
specifier|public
class|class
name|ExceptionHandlers
block|{
comment|/**      * The ExceptionHandler instances.      * Key: InstructionHandle objects, Values: HashSet<ExceptionHandler> instances.      */
specifier|private
name|Map
argument_list|<
name|InstructionHandle
argument_list|,
name|Set
argument_list|<
name|ExceptionHandler
argument_list|>
argument_list|>
name|exceptionhandlers
decl_stmt|;
comment|/**      * Constructor. Creates a new ExceptionHandlers instance.      */
specifier|public
name|ExceptionHandlers
parameter_list|(
name|MethodGen
name|mg
parameter_list|)
block|{
name|exceptionhandlers
operator|=
operator|new
name|HashMap
argument_list|<
name|InstructionHandle
argument_list|,
name|Set
argument_list|<
name|ExceptionHandler
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|CodeExceptionGen
index|[]
name|cegs
init|=
name|mg
operator|.
name|getExceptionHandlers
argument_list|()
decl_stmt|;
for|for
control|(
name|CodeExceptionGen
name|ceg
range|:
name|cegs
control|)
block|{
name|ExceptionHandler
name|eh
init|=
operator|new
name|ExceptionHandler
argument_list|(
name|ceg
operator|.
name|getCatchType
argument_list|()
argument_list|,
name|ceg
operator|.
name|getHandlerPC
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|InstructionHandle
name|ih
init|=
name|ceg
operator|.
name|getStartPC
argument_list|()
init|;
name|ih
operator|!=
name|ceg
operator|.
name|getEndPC
argument_list|()
operator|.
name|getNext
argument_list|()
condition|;
name|ih
operator|=
name|ih
operator|.
name|getNext
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|ExceptionHandler
argument_list|>
name|hs
decl_stmt|;
name|hs
operator|=
name|exceptionhandlers
operator|.
name|get
argument_list|(
name|ih
argument_list|)
expr_stmt|;
if|if
condition|(
name|hs
operator|==
literal|null
condition|)
block|{
name|hs
operator|=
operator|new
name|HashSet
argument_list|<
name|ExceptionHandler
argument_list|>
argument_list|()
expr_stmt|;
name|exceptionhandlers
operator|.
name|put
argument_list|(
name|ih
argument_list|,
name|hs
argument_list|)
expr_stmt|;
block|}
name|hs
operator|.
name|add
argument_list|(
name|eh
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Returns all the ExceptionHandler instances representing exception      * handlers that protect the instruction ih.      */
specifier|public
name|ExceptionHandler
index|[]
name|getExceptionHandlers
parameter_list|(
name|InstructionHandle
name|ih
parameter_list|)
block|{
name|Set
argument_list|<
name|ExceptionHandler
argument_list|>
name|hsSet
init|=
name|exceptionhandlers
operator|.
name|get
argument_list|(
name|ih
argument_list|)
decl_stmt|;
if|if
condition|(
name|hsSet
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|ExceptionHandler
index|[
literal|0
index|]
return|;
block|}
return|return
name|hsSet
operator|.
name|toArray
argument_list|(
operator|new
name|ExceptionHandler
index|[
name|hsSet
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
block|}
end_class

end_unit


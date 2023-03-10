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
name|Map
import|;
end_import

begin_comment
comment|/**  * This repository is used in situations where a Class is created outside the realm of a ClassLoader. Classes are loaded  * from the file systems using the paths specified in the given class path. By default, this is the value returned by  * ClassPath.getClassPath().  *<p>  * This repository uses a factory design, allowing it to maintain a collection of different classpaths, and as such It  * is designed to be used as a singleton per classpath.  *</p>  *  * @see org.apache.bcel.Repository  */
end_comment

begin_class
specifier|public
class|class
name|SyntheticRepository
extends|extends
name|MemorySensitiveClassPathRepository
block|{
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|ClassPath
argument_list|,
name|SyntheticRepository
argument_list|>
name|MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// CLASSPATH X REPOSITORY
specifier|public
specifier|static
name|SyntheticRepository
name|getInstance
parameter_list|()
block|{
return|return
name|getInstance
argument_list|(
name|ClassPath
operator|.
name|SYSTEM_CLASS_PATH
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SyntheticRepository
name|getInstance
parameter_list|(
specifier|final
name|ClassPath
name|classPath
parameter_list|)
block|{
return|return
name|MAP
operator|.
name|computeIfAbsent
argument_list|(
name|classPath
argument_list|,
name|SyntheticRepository
operator|::
operator|new
argument_list|)
return|;
block|}
specifier|private
name|SyntheticRepository
parameter_list|(
specifier|final
name|ClassPath
name|path
parameter_list|)
block|{
name|super
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit


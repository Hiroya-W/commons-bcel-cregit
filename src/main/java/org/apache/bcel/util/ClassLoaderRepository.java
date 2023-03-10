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
name|Utility
import|;
end_import

begin_comment
comment|/**  * The repository maintains information about which classes have been loaded.  *  * It loads its data from the ClassLoader implementation passed into its constructor.  *  * @see org.apache.bcel.Repository  */
end_comment

begin_class
specifier|public
class|class
name|ClassLoaderRepository
implements|implements
name|Repository
block|{
specifier|private
specifier|final
name|java
operator|.
name|lang
operator|.
name|ClassLoader
name|loader
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|JavaClass
argument_list|>
name|loadedClasses
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// CLASSNAME X JAVACLASS
specifier|public
name|ClassLoaderRepository
parameter_list|(
specifier|final
name|java
operator|.
name|lang
operator|.
name|ClassLoader
name|loader
parameter_list|)
block|{
name|this
operator|.
name|loader
operator|=
name|loader
expr_stmt|;
block|}
comment|/**      * Clear all entries from cache.      */
annotation|@
name|Override
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|loadedClasses
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|/**      * Find an already defined JavaClass.      */
annotation|@
name|Override
specifier|public
name|JavaClass
name|findClass
parameter_list|(
specifier|final
name|String
name|className
parameter_list|)
block|{
return|return
name|loadedClasses
operator|.
name|get
argument_list|(
name|className
argument_list|)
return|;
block|}
comment|/*      * @return null      */
annotation|@
name|Override
specifier|public
name|ClassPath
name|getClassPath
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|JavaClass
name|loadClass
parameter_list|(
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
return|return
name|loadClass
argument_list|(
name|clazz
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Lookup a JavaClass object from the Class Name provided.      */
annotation|@
name|Override
specifier|public
name|JavaClass
name|loadClass
parameter_list|(
specifier|final
name|String
name|className
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|String
name|classFile
init|=
name|Utility
operator|.
name|packageToPath
argument_list|(
name|className
argument_list|)
decl_stmt|;
name|JavaClass
name|RC
init|=
name|findClass
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|RC
operator|!=
literal|null
condition|)
block|{
return|return
name|RC
return|;
block|}
try|try
init|(
name|InputStream
name|is
init|=
name|loader
operator|.
name|getResourceAsStream
argument_list|(
name|classFile
operator|+
name|JavaClass
operator|.
name|EXTENSION
argument_list|)
init|)
block|{
if|if
condition|(
name|is
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ClassNotFoundException
argument_list|(
name|className
operator|+
literal|" not found."
argument_list|)
throw|;
block|}
specifier|final
name|ClassParser
name|parser
init|=
operator|new
name|ClassParser
argument_list|(
name|is
argument_list|,
name|className
argument_list|)
decl_stmt|;
name|RC
operator|=
name|parser
operator|.
name|parse
argument_list|()
expr_stmt|;
name|storeClass
argument_list|(
name|RC
argument_list|)
expr_stmt|;
return|return
name|RC
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ClassNotFoundException
argument_list|(
name|className
operator|+
literal|" not found: "
operator|+
name|e
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Remove class from repository      */
annotation|@
name|Override
specifier|public
name|void
name|removeClass
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|)
block|{
name|loadedClasses
operator|.
name|remove
argument_list|(
name|clazz
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Store a new JavaClass into this Repository.      */
annotation|@
name|Override
specifier|public
name|void
name|storeClass
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|)
block|{
name|loadedClasses
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getClassName
argument_list|()
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|setRepository
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit


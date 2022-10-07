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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Modifier
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

begin_comment
comment|/**  * Java interpreter replacement, i.e., wrapper that uses its own ClassLoader to modify/generate classes as they're  * requested. You can take this as a template for your own applications.  *<p>  * Call this wrapper with:  *</p>  *  *<pre>  * java org.apache.bcel.util.JavaWrapper&lt;real.class.name&gt; [arguments]  *</pre>  *<p>  * To use your own class loader you can set the "bcel.classloader" system property.  *</p>  *  *<pre>  * java org.apache.bcel.util.JavaWrapper -Dbcel.classloader=foo.MyLoader&lt;real.class.name&gt; [arguments]  *</pre>  *  * @see ClassLoader  */
end_comment

begin_class
specifier|public
class|class
name|JavaWrapper
block|{
specifier|private
specifier|static
name|java
operator|.
name|lang
operator|.
name|ClassLoader
name|getClassLoader
parameter_list|()
block|{
specifier|final
name|String
name|s
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"bcel.classloader"
argument_list|)
decl_stmt|;
if|if
condition|(
name|StringUtils
operator|.
name|isEmpty
argument_list|(
name|s
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"The property 'bcel.classloader' must be defined"
argument_list|)
throw|;
block|}
try|try
block|{
return|return
operator|(
name|java
operator|.
name|lang
operator|.
name|ClassLoader
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|s
argument_list|)
operator|.
name|newInstance
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|e
operator|.
name|toString
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Default main method used as wrapper, expects the fully qualified class name of the real class as the first argument.      */
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
throws|throws
name|Exception
block|{
comment|/*          * Expects class name as first argument, other arguments are by-passed.          */
if|if
condition|(
name|argv
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Missing class name."
argument_list|)
expr_stmt|;
return|return;
block|}
specifier|final
name|String
name|className
init|=
name|argv
index|[
literal|0
index|]
decl_stmt|;
specifier|final
name|String
index|[]
name|newArgv
init|=
operator|new
name|String
index|[
name|argv
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|argv
argument_list|,
literal|1
argument_list|,
name|newArgv
argument_list|,
literal|0
argument_list|,
name|newArgv
operator|.
name|length
argument_list|)
expr_stmt|;
operator|new
name|JavaWrapper
argument_list|()
operator|.
name|runMain
argument_list|(
name|className
argument_list|,
name|newArgv
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|final
name|java
operator|.
name|lang
operator|.
name|ClassLoader
name|loader
decl_stmt|;
specifier|public
name|JavaWrapper
parameter_list|()
block|{
name|this
argument_list|(
name|getClassLoader
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|JavaWrapper
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
comment|/**      * Runs the main method of the given class with the arguments passed in argv      *      * @param className the fully qualified class name      * @param argv the arguments just as you would pass them directly      */
specifier|public
name|void
name|runMain
parameter_list|(
specifier|final
name|String
name|className
parameter_list|,
specifier|final
name|String
index|[]
name|argv
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|cl
init|=
name|loader
operator|.
name|loadClass
argument_list|(
name|className
argument_list|)
decl_stmt|;
name|Method
name|method
init|=
literal|null
decl_stmt|;
try|try
block|{
name|method
operator|=
name|cl
operator|.
name|getMethod
argument_list|(
literal|"main"
argument_list|,
name|argv
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
comment|/*              * Method main is sane ?              */
specifier|final
name|int
name|m
init|=
name|method
operator|.
name|getModifiers
argument_list|()
decl_stmt|;
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
name|r
init|=
name|method
operator|.
name|getReturnType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|Modifier
operator|.
name|isPublic
argument_list|(
name|m
argument_list|)
operator|&&
name|Modifier
operator|.
name|isStatic
argument_list|(
name|m
argument_list|)
operator|)
operator|||
name|Modifier
operator|.
name|isAbstract
argument_list|(
name|m
argument_list|)
operator|||
name|r
operator|!=
name|Void
operator|.
name|TYPE
condition|)
block|{
throw|throw
operator|new
name|NoSuchMethodException
argument_list|()
throw|;
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|NoSuchMethodException
name|no
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"In class "
operator|+
name|className
operator|+
literal|": public static void main(String[] argv) is not defined"
argument_list|)
expr_stmt|;
return|return;
block|}
try|try
block|{
name|method
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
operator|(
name|Object
index|[]
operator|)
name|argv
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit


begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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

begin_comment
comment|/* ====================================================================  * The Apache Software License, Version 1.1  *  * Copyright (c) 2001 The Apache Software Foundation.  All rights  * reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions  * are met:  *  * 1. Redistributions of source code must retain the above copyright  *    notice, this list of conditions and the following disclaimer.  *  * 2. Redistributions in binary form must reproduce the above copyright  *    notice, this list of conditions and the following disclaimer in  *    the documentation and/or other materials provided with the  *    distribution.  *  * 3. The end-user documentation included with the redistribution,  *    if any, must include the following acknowledgment:  *       "This product includes software developed by the  *        Apache Software Foundation (http://www.apache.org/)."  *    Alternately, this acknowledgment may appear in the software itself,  *    if and wherever such third-party acknowledgments normally appear.  *  * 4. The names "Apache" and "Apache Software Foundation" and  *    "Apache BCEL" must not be used to endorse or promote products  *    derived from this software without prior written permission. For  *    written permission, please contact apache@apache.org.  *  * 5. Products derived from this software may not be called "Apache",  *    "Apache BCEL", nor may "Apache" appear in their name, without  *    prior written permission of the Apache Software Foundation.  *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF  * SUCH DAMAGE.  * ====================================================================  *  * This software consists of voluntary contributions made by many  * individuals on behalf of the Apache Software Foundation.  For more  * information on the Apache Software Foundation, please see  *<http://www.apache.org/>.  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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
name|HashMap
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
name|*
import|;
end_import

begin_comment
comment|/**  * This repository is used in situations where a Class is created  * outside the realm of a ClassLoader. Classes are loaded from  * the file systems using the paths specified in the given  * class path. By default, this is the value returned by  * ClassPath.getClassPath().  *<br>  * It is designed to be used as a singleton, however it  * can also be used with custom classpaths.  *  * @version $Id$  * @author<A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>  * @author David Dixon-Peugh  */
end_comment

begin_class
specifier|public
class|class
name|SyntheticRepository
implements|implements
name|Repository
block|{
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_PATH
init|=
name|ClassPath
operator|.
name|getClassPath
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|HashMap
name|_instances
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|// CLASSPATH X REPOSITORY
specifier|private
name|ClassPath
name|_path
init|=
literal|null
decl_stmt|;
specifier|private
name|HashMap
name|_loadedClasses
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|// CLASSNAME X JAVACLASS
specifier|private
name|SyntheticRepository
parameter_list|(
name|ClassPath
name|path
parameter_list|)
block|{
name|_path
operator|=
name|path
expr_stmt|;
block|}
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
name|ClassPath
name|classPath
parameter_list|)
block|{
name|SyntheticRepository
name|rep
init|=
operator|(
name|SyntheticRepository
operator|)
name|_instances
operator|.
name|get
argument_list|(
name|classPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|rep
operator|==
literal|null
condition|)
block|{
name|rep
operator|=
operator|new
name|SyntheticRepository
argument_list|(
name|classPath
argument_list|)
expr_stmt|;
name|_instances
operator|.
name|put
argument_list|(
name|classPath
argument_list|,
name|rep
argument_list|)
expr_stmt|;
block|}
return|return
name|rep
return|;
block|}
comment|/**    * Store a new JavaClass instance into this Repository.    */
specifier|public
name|void
name|storeClass
parameter_list|(
name|JavaClass
name|clazz
parameter_list|)
block|{
name|_loadedClasses
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
comment|/**    * Remove class from repository    */
specifier|public
name|void
name|removeClass
parameter_list|(
name|JavaClass
name|clazz
parameter_list|)
block|{
name|_loadedClasses
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
comment|/**    * Find an already defined JavaClass.    */
specifier|public
name|JavaClass
name|findClass
parameter_list|(
name|String
name|className
parameter_list|)
block|{
return|return
operator|(
name|JavaClass
operator|)
name|_loadedClasses
operator|.
name|get
argument_list|(
name|className
argument_list|)
return|;
block|}
comment|/**    * Lookup a JavaClass object from the class name provided.    */
specifier|public
name|JavaClass
name|loadClass
parameter_list|(
name|String
name|className
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
if|if
condition|(
name|className
operator|==
literal|null
operator|||
name|className
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Invalid class name "
operator|+
name|className
argument_list|)
throw|;
block|}
name|className
operator|=
name|className
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'.'
argument_list|)
expr_stmt|;
comment|// Just in case, canonical form
try|try
block|{
return|return
name|loadClass
argument_list|(
name|_path
operator|.
name|getInputStream
argument_list|(
name|className
argument_list|)
argument_list|,
name|className
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ClassNotFoundException
argument_list|(
literal|"Exception while looking for class "
operator|+
name|className
operator|+
literal|": "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/**    * Try to find class source via getResourceAsStream().    * @see Class    * @return JavaClass object for given runtime class    */
specifier|public
name|JavaClass
name|loadClass
parameter_list|(
name|Class
name|clazz
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
name|String
name|className
init|=
name|clazz
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|className
decl_stmt|;
name|int
name|i
init|=
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
return|return
name|loadClass
argument_list|(
name|clazz
operator|.
name|getResourceAsStream
argument_list|(
name|name
operator|+
literal|".class"
argument_list|)
argument_list|,
name|className
argument_list|)
return|;
block|}
specifier|private
name|JavaClass
name|loadClass
parameter_list|(
name|InputStream
name|is
parameter_list|,
name|String
name|className
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
name|JavaClass
name|clazz
init|=
name|findClass
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|!=
literal|null
condition|)
block|{
return|return
name|clazz
return|;
block|}
try|try
block|{
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
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
name|clazz
operator|=
name|parser
operator|.
name|parse
argument_list|()
expr_stmt|;
name|storeClass
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
return|return
name|clazz
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ClassNotFoundException
argument_list|(
literal|"Exception while looking for class "
operator|+
name|className
operator|+
literal|": "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
throw|throw
operator|new
name|ClassNotFoundException
argument_list|(
literal|"SyntheticRepository could not load "
operator|+
name|className
argument_list|)
throw|;
block|}
comment|/** Clear all entries from cache.    */
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|_loadedClasses
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit


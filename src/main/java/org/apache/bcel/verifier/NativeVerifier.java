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
name|verifier
package|;
end_package

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
comment|/**  * The NativeVerifier class implements a main(String[] args) method that's roughly compatible to the one in the Verifier  * class, but that uses the JVM's internal verifier for its class file verification. This can be used for comparison  * runs between the JVM-internal verifier and JustIce.  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|NativeVerifier
block|{
comment|/**      * Works only on the first argument.      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|args
parameter_list|)
block|{
if|if
condition|(
name|args
operator|.
name|length
operator|!=
literal|1
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Verifier front-end: need exactly one argument."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
specifier|final
name|int
name|dotclasspos
init|=
name|args
index|[
literal|0
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|".class"
argument_list|)
decl_stmt|;
if|if
condition|(
name|dotclasspos
operator|!=
operator|-
literal|1
condition|)
block|{
name|args
index|[
literal|0
index|]
operator|=
name|args
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|dotclasspos
argument_list|)
expr_stmt|;
block|}
name|args
index|[
literal|0
index|]
operator|=
name|Utility
operator|.
name|pathToPackage
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|// System.out.println(args[0]);
try|try
block|{
name|Class
operator|.
name|forName
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|ExceptionInInitializerError
name|eiie
parameter_list|)
block|{
comment|// subclass of LinkageError!
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"NativeVerifier: ExceptionInInitializerError encountered on '"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|eiie
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|LinkageError
name|le
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"NativeVerifier: LinkageError encountered on '"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|le
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|ClassNotFoundException
name|cnfe
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"NativeVerifier: FILE NOT FOUND: '"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|t
parameter_list|)
block|{
comment|// OK to catch Throwable here as we call exit.
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"NativeVerifier: Unspecified verification error on '"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|"'."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"NativeVerifier: Class file '"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|"' seems to be okay."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|/**      * This class must not be instantiated.      */
specifier|private
name|NativeVerifier
parameter_list|()
block|{
block|}
block|}
end_class

end_unit


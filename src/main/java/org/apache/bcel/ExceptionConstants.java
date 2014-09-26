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
package|;
end_package

begin_comment
comment|/**  * Exception constants.  *  * @version $Id$  * @author  E. Haase  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExceptionConstants
block|{
comment|/** The mother of all exceptions      */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|Throwable
argument_list|>
name|THROWABLE
init|=
name|Throwable
operator|.
name|class
decl_stmt|;
comment|/** Super class of any run-time exception      */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|RuntimeException
argument_list|>
name|RUNTIME_EXCEPTION
init|=
name|RuntimeException
operator|.
name|class
decl_stmt|;
comment|/** Super class of any linking exception (aka Linkage Error)      */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|LinkageError
argument_list|>
name|LINKING_EXCEPTION
init|=
name|LinkageError
operator|.
name|class
decl_stmt|;
comment|/** Linking Exceptions      */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ClassCircularityError
argument_list|>
name|CLASS_CIRCULARITY_ERROR
init|=
name|ClassCircularityError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ClassFormatError
argument_list|>
name|CLASS_FORMAT_ERROR
init|=
name|ClassFormatError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ExceptionInInitializerError
argument_list|>
name|EXCEPTION_IN_INITIALIZER_ERROR
init|=
name|ExceptionInInitializerError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|IncompatibleClassChangeError
argument_list|>
name|INCOMPATIBLE_CLASS_CHANGE_ERROR
init|=
name|IncompatibleClassChangeError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|AbstractMethodError
argument_list|>
name|ABSTRACT_METHOD_ERROR
init|=
name|AbstractMethodError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|IllegalAccessError
argument_list|>
name|ILLEGAL_ACCESS_ERROR
init|=
name|IllegalAccessError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|InstantiationError
argument_list|>
name|INSTANTIATION_ERROR
init|=
name|InstantiationError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|NoSuchFieldError
argument_list|>
name|NO_SUCH_FIELD_ERROR
init|=
name|NoSuchFieldError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|NoSuchMethodError
argument_list|>
name|NO_SUCH_METHOD_ERROR
init|=
name|NoSuchMethodError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|NoClassDefFoundError
argument_list|>
name|NO_CLASS_DEF_FOUND_ERROR
init|=
name|NoClassDefFoundError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|UnsatisfiedLinkError
argument_list|>
name|UNSATISFIED_LINK_ERROR
init|=
name|UnsatisfiedLinkError
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|VerifyError
argument_list|>
name|VERIFY_ERROR
init|=
name|VerifyError
operator|.
name|class
decl_stmt|;
comment|/* UnsupportedClassVersionError is new in JDK 1.2 */
comment|//public static final Class UnsupportedClassVersionError = UnsupportedClassVersionError.class;
comment|/** Run-Time Exceptions       */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|NullPointerException
argument_list|>
name|NULL_POINTER_EXCEPTION
init|=
name|NullPointerException
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ArrayIndexOutOfBoundsException
argument_list|>
name|ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION
init|=
name|ArrayIndexOutOfBoundsException
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ArithmeticException
argument_list|>
name|ARITHMETIC_EXCEPTION
init|=
name|ArithmeticException
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|NegativeArraySizeException
argument_list|>
name|NEGATIVE_ARRAY_SIZE_EXCEPTION
init|=
name|NegativeArraySizeException
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|ClassCastException
argument_list|>
name|CLASS_CAST_EXCEPTION
init|=
name|ClassCastException
operator|.
name|class
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|IllegalMonitorStateException
argument_list|>
name|ILLEGAL_MONITOR_STATE
init|=
name|IllegalMonitorStateException
operator|.
name|class
decl_stmt|;
comment|/** Pre-defined exception arrays according to chapters 5.1-5.4 of the Java Virtual      * Machine Specification       */
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|EXCS_CLASS_AND_INTERFACE_RESOLUTION
init|=
block|{
name|NO_CLASS_DEF_FOUND_ERROR
block|,
name|CLASS_FORMAT_ERROR
block|,
name|VERIFY_ERROR
block|,
name|ABSTRACT_METHOD_ERROR
block|,
name|EXCEPTION_IN_INITIALIZER_ERROR
block|,
name|ILLEGAL_ACCESS_ERROR
block|}
decl_stmt|;
comment|// Chapter 5.1
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|EXCS_FIELD_AND_METHOD_RESOLUTION
init|=
block|{
name|NO_SUCH_FIELD_ERROR
block|,
name|ILLEGAL_ACCESS_ERROR
block|,
name|NO_SUCH_METHOD_ERROR
block|}
decl_stmt|;
comment|// Chapter 5.2
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|EXCS_INTERFACE_METHOD_RESOLUTION
init|=
operator|new
name|Class
index|[
literal|0
index|]
decl_stmt|;
comment|// Chapter 5.3 (as below)
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|EXCS_STRING_RESOLUTION
init|=
operator|new
name|Class
index|[
literal|0
index|]
decl_stmt|;
comment|// Chapter 5.4 (no errors but the ones that _always_ could happen! How stupid.)
specifier|public
specifier|static
specifier|final
name|Class
argument_list|<
name|?
argument_list|>
index|[]
name|EXCS_ARRAY_EXCEPTION
init|=
block|{
name|NULL_POINTER_EXCEPTION
block|,
name|ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION
block|}
decl_stmt|;
block|}
end_interface

end_unit


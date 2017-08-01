/*
 * Copyright 2017 Ryan Y.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.test.boundaries.Requestor
import org.test.doubles.IdentityHandler
import org.test.requests.Request

class TransactionTest {
  private lateinit var handler: IdentityHandler
  private lateinit var transaction: Transaction

  @Before
  fun housekeeping() {
    this.handler = IdentityHandler()
    this.transaction = Transaction(this.handler)
  }

  @Test
  fun testImplementsRequestor() {
    assertThat(this.transaction, instanceOf(Requestor::class.java))
  }

  @Test
  fun testUnsuccessfulWithoutName() {
    this.handleEmptyRequest()
    assertThat(this.handler.response.success, equalTo(false))
  }

  private fun handleEmptyRequest() {
    this.handleRequestWith("")
  }

  private fun handleRequestWith(name: String) {
    val request = Request(name, "")
    this.transaction.perform(request)
  }

  @Test
  fun testFailedMessageWithoutName() {
    this.handleEmptyRequest()
    assertThat(this.handler.response.message, equalTo("must have a name"))
  }

  @Test
  fun testUnsuccessfulWithoutCategory() {
    this.handleNamedUncategorisedRequest()
    assertThat(this.handler.response.success, equalTo(false))
  }

  private fun handleNamedUncategorisedRequest() {
    this.handleRequestWith("my name")
  }

  @Test
  fun testFailedMessageWithoutCategory() {
    this.handleNamedUncategorisedRequest()
    assertThat(this.handler.response.message, equalTo("must have a category"))
  }
}

Feature: Checkout
  As a customer
  I want a checkout machine that calculates an accurate price
  So that I pay the right price for what I'm buying

  Scenario: Checkout a banana
    Note the unnecessary details in this scenario.  Most scenarios don't need these kinds of details to describe the
    behavior (which in this case, is that the total should be the sum of all the goods that are checked out).
    Also note that you can write notes before the first Given/When/Then step.

    Given the price of a "banana" is 40c
    When I checkout 1 "banana"
    Then the total price should be 40c

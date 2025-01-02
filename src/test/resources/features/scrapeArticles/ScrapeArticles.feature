Feature: Scrape Opinion Articles

  Background: I am on the home page
    Given I visit the home page and click on agree button

  Scenario: Scrape the first five articles
    Given I click on the sidebar toggle button and navigate to the opinion section
    Then I fetch and verify the first five articles along with their content and download cover image if present
    And I translate all the article titles to English and print them
    And Count the repeated words whose count is greater than 2

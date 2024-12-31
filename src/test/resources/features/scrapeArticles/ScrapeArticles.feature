@ScrapeArticles
Feature: Scrape Opinion Articles

  Background: I am on the home page

  Scenario: Scrape the first five articles
    Given I visit the home page and click on agree button
    Then I click on the sidebar toggle button and navigate to the opinion section
    And I fetch the first five articles in Spanish

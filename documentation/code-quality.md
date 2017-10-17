# Code quality

[Back to top](../README.md)

- [Code quality](#code-quality)
    - [Group workflow](#group-workflow)
        - [Pair Programming](#pair-programming)
        - [Code Reviews](#code-reviews)
        - [Git Workflow](#git-workflow)
        - [Continuous integration](#continuous-integration)
        - [Test-driven Development](#test-driven-development)
        - [Behaviour-driven Development](#behaviour-driven-development)
        - [Software Versioning](#software-versioning)

This document describes the strategies and methodologies that the team has chosen to adhere to with the intention of maintaining a higher standard of quality for the main product.

Each methodology has been chosen with a rationale of what the team can manage to adhere to, even though there are more strategies available to reach an even higher standard of code quality.

Key values that the group has in mind are:

- Readability - Code is meant for humans to read, not machines to interpret. Principles that are kept in mind are:
    - [Law of demeter](https://en.wikipedia.org/wiki/Law_of_Demeter)
    - [Refactoring Code](https://en.wikipedia.org/wiki/Code_refactoring)
    - [Naming conventions](https://en.wikipedia.org/wiki/Naming_convention_(programming))
- Modularity - Code separated into modules mean that they can be switched out for other modules in the future. Principles that are kept in mind are:
    - [Separation of Concerns](https://en.wikipedia.org/wiki/Separation_of_concerns)
    - [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle)
    - [High cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))
    - [Low coupling](https://en.wikipedia.org/wiki/Coupling_(computer_programming))
- Extensibility - Code should be independent enough so that it functions on its own, but is still open for extensions and further development, without breaking the previous implementations. Principles that are kept in mind are:
    - [Open Closed Principle](https://en.wikipedia.org/wiki/Open/closed_principle)
    - [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle)
- Reusability - Code should be reusable and context-independent, which leads to less lines of code and fewer bugs. Principles that are kept in mind are:
    - [Composition over Inheritance](https://en.wikipedia.org/wiki/Composition_over_inheritance)
    - [High cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))
    - [Low coupling](https://en.wikipedia.org/wiki/Coupling_(computer_programming))
    - [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself)
- Testability - Code that is testable is also more modular and extensible, which means that testable code is SOLID code in many aspects. Principles that are kept in mind are:
    - [Inversion of Control](https://en.wikipedia.org/wiki/Inversion_of_control)
    - [Composition over Inheritance](https://en.wikipedia.org/wiki/Composition_over_inheritance)

## Group workflow

The list below shortly summarizes the different strategies and how they will be used. Each strategy also has their own describing section that goes more in-depth to explain and reflect on the benefits and trade-offs of said strategy.

- Pair programming will be a focal point during the work process
- All code will be reviewed by another group member according to [Code reviews](./code-reviews.md)
- Every contributor will employ the [Git workflow](./git-workflow.md)
- Continuous integration is enforced
    - Code will not pushed to the repository unless all tests are passing
- TDD is enforced for everyone
    - Units will be checked using Unit Tests
    - Modules will be checked using Integration tests
    - Components will be checked using System tests
- BDD will be used **if** it is necessary
- Software versioning, i.e A.B.C
    - A is a breaking release
    - B is a major release
    - C is patches and bug fixes.

---

### Pair Programming

All team members are to develop the product in pairs of two utilizing [Pair Programming](http://www.extremeprogramming.org/rules/pair.html). The reason for this decision is simple - pair programming means that two minds are implementing the same feature, which will lead to the produced code being more motivated and reasonable.

#### Benefits

Pair programming increases the **readability** and **maintainability** of the codebase, which will mean that the risk of refactoring later on is mitigated. Having a *"driver"* and *"navigator"* work on the same lines of code minimizes poor code quality through factors such as:

- minimizing the amount of bad naming choices
- noticing compile time errors immediately
- rational, well-motivated and effective implementations of design patterns
- lessens the rate of bugs that can creep into the codebase

Overall, the main benefit of utilizing pair programming is that it minimizes the amount of technical debt, and, therefore, increases the code quality by a noticeable degree when compared to having the team developing individually.

#### Trade-offs

The main trade-off of having two developers on the same task when developing is that it means that the lines of code (LOC) is halved, thus increasing the risk of not producing the estimated value of the current sprint. However, as mentioned above, the code quality instead increases of the produced code, which means that the risk of having to revise/refactor the codebase in later sprints is mitigated.

Therefore, the team chooses quality over quantity, as to avoid the risk of creating a big ball of mud and instead maintain a good and maintainable structure of the codebase.

---

### Code Reviews

Every single line of code has to be reviewed by a team member whom has not been involved with the that is reviewed. This is for reassuring that the code is **readable** and that the logic behind it is reasonable to the unfamiliar eye. More information as to how the code review is done can be found in the [Code Review document](./code-review.md).

#### Benefits

By having at least one unfamiliar eye to review the produced code will contribute to a significant increase of code quality. This is due to the fact that the person who reviews the code has to be able to understand everything that has been written, without the help of the developer whom was the writer. The reviewer therefore acts as a filter that filters out unnecessary code and odd logic, thus mitigating the risk of gaining technical debt and bugs creeping into the deployed product. Additionally, the reviews increase the possibilities of immediate refactoring and revising of the coding logic, which means that the code will be more readable, maintainable, extensible and reusable.

#### Trade-offs

The major trade-off of having code reviews is the aspect of time, as reviewing a codebase requires a lot of time and focus from the reviewer, as well as the reviewee when the reviewed code has to be revised according to the reviewed results.

Additionally, having your code reviewed entails you being subject to constructive feedback. If the feedback is poorly given, or if it is misinterpreted, unnecessary friction among the team members can occur. This may lead to group dynamic pollution which, in return, can lead to conflicts and future teamwork disabilities.

---

### Git Workflow

To have a structured, or rather, less jumbled version control history, we have come up with a git workflow for managing feature development, hotfixes and development to production deployment.

#### Benefits

This workflow is primarily useful out of the **readability** aspect, because by having a structured deployment history allows for easy regression if a component would malfunction at a later stage. Also, by having these guidelines, a consistent version history will be easier to maintain, as long as everybody follows them. Maintaining the **testability** of the code is also enforced, as to ensure that the code that is deployed is functional.

#### Trade-offs

A major trade-off for having a large and structured workflow for git is that it takes a bit of time to learn it. Due to this learning process, errors are prone to happen in the beginning and some bad habits may come out of these errors if repeated. This entails the loss of the sought after structure already in the beginning, which indicates that having a too difficult workflow is equal to shooting yourself in the foot.

---

### Continuous integration

Being able to continuously deploy new features are crucial when working agile. Therefore, we decided on trying out a continuous integration tool to make the process easier. We decided on [Travis CI](https://travis-ci.org/) as it is free for students to use. The setup has been a bit of a hassle, as the project consists of multiple modules and some are dependent on each other to function. Therefore, the integration process had to be configured accordingly and the tools fails the testing process if one module isn't functioning.

#### Benefits

When an environment for continuous integration has been set up, the deployment process becomes extremely streamlined. If the supplied tests for Travis fails when a pull request has been submitted, then that PR will automatically be declined until all tests pass. Thanks to this, the important and cumbersome step of manually running the test suite, as to confirm that the code functions, is removed from the individual and instead automated. In return, the code only needs to be reviewed and then accepted for merging, which can save a lot of time in the long run.

Also, with having less of an overhead when deploying and taking a good test coverage into account, more resources can be spent to make the code quality higher through refactoring and additional development. In return, the code then becomes more **readable**, **maintainable** and **extensible**

#### Trade-offs

As time can be saved during deployment after implementing continuous integration, the path to successfully configuring the process can take quite some time without any experience. Therefore, it is important to gauge wether spending the time setting up a continuous integration environment is outweighed by the saved time in the future.

Additionally, in order to use continuous integration, the code needs to be **testable** as much as possible, as to keep the **maintainability** and **extensibility** levels high so that more features can be developed with the same ease.

---

### Test-driven Development

In conjunction with having continuous integration, having tests are a necessity in order to validate that the newly deployed code does not break anything that already works. Therefore, the decision on using TDD became clear as this form of development style automatically produces tests in along with the produced code, which is exactly what is needed as Travis run the test suite on every new PR.

#### Benefits

There is an array of different benefits to using TDD. Firstly, the code coverage automatically goes up as no code is developed without passing a previous failing test, creating a better safety net for testing new features and making sure that nothing of the already existing code breaks. This entails that the **testability** of the code greatly increases.

Secondly, by only producing code that passes a single assertion, the code that is produced is meant to only solve the problem that the test needs to pass. This entails a less error-prone code base and fewer bugs to find and resolve. The benefit of having less bugs is that the code base becomes more **maintainable**. Having less bugs means that new code that is dependent on the code base becomes less error-prone, which entails an increase in **extensibility**.

Thirdly, as the main workflow of TDD is *fail a test*, *pass the test* and *refactor the code **(including the test)***, there will be less of a **maintainability issue**. This is due to the code being revised as soon as it is passing its test, creating less of an overhead for future development.

#### Trade-offs

Even if TDD has a lot of different benefits, there are some negative aspects to it as well. Firstly, writing tests for every piece of code means that the lines of code is about doubled. More code means a higher risk of bugs, which makes both **extensibility** and **maintainability** harder as the code base grows larger. This also means that if the test has bugs, then the code probably has them as well. As the code covers the test case, the code functions according to the the test. However, if the test case is dysfunctional and does not cover all outcomes, then the code will malfunction in production.

Secondly, if a feature's implementation is not thoroughly planned out beforehand, writing a sufficient test to begin with becomes very difficult. Therefore, sometimes a lot of time can be spent banging your head against the wall until you reach the conclusion to go back to the drawing board or to just smack up a solution without any tests. This means that a lot of unnecessary time can be spent trying to write tests that might not even come to exist. Additionally, not having tests entail a lowered **reusability rate**, as code that does not have covering tests are not safe to reuse in other implementations. Also, quite a bit of time needs to be spent on the actual planning of a feature. However, this is seen as a prerequisite for successful development and therefore is not taken into account.

---

### Behaviour-driven Development

Making sure that the implemented features correlate with the PO's requests in the [product backlog](./product-backlog.md) is very important in order to produce something of value. This is where BDD shines through. With its *"stakeholder first"* mindset, producing value is as good as inevitable.

#### Benefits

Firstly, by basing the use cases and tasks off of the different user stories in the backlog, and conforming the produced code to be readable by outsourced individuals, the possibility of quality feedback greatly increases. By writing tests that conform to these requirements, it is possible to directly ask the stakeholder to read the tests and say whether their requests have been met. In return, the feedback loop gets a bit tighter and more streamlined.

Secondly, implementing BDD means that the vocabulary is adapted in order to keep a common language between the stakeholder and the development team. This common language then facilitates the communication between the PO and the team, which lessens the risk of loss in communication.

#### Trade-offs

The main negative side to BDD is that it requires quite a bit of resources in order to streamline the process, which means time and effort is taken from the actual development process. This mainly comes in the form of  administrative work, which means more files to maintain. In return, this leads to a smaller **maintainability rate**.

Also, adapting code such as tests to be read by outsourced people requires more verbose and long test code, which also requires more resources, like time and effort. As long as all user stories are well defined, spending these resources on new feature development could, in theory, mean more produced value. Therefore, for shorter projects using BDD needs to be thoroughly discussed to weigh the benefits against the trade-offs.

---

### Software Versioning

Versioning of a product is something that is important in order to quickly identify wether the user has the latest version or not. Therefore, this project follows the standard [Software Versioning](https://en.wikipedia.org/wiki/Software_versioning) with a three-stage-sequence, A.B.C, where A is major changes, B is minor changes and C is hotfixes.

#### Benefits

There are not really any major benefits nor setbacks to which versioning is used. However, one benefit of using this standard is that it is well recognized by other people and therefore leads to less misinterpretation. 
# Code quality

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
- Sequenced versioning, i.e A.B.C
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

---

### Continuous integration

---

### Test-driven Development

---

### Behavior-driven Development

---

### Sequenced Versioning
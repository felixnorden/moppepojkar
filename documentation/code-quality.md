# Code quality

This document describes the strategies and methodologies that the team has chosen to adhere to with the intention of maintaining a higher standard of quality for the main product.

Each methodology has been chosen with a rationale of what the team can manage to adhere to, even though there are more strategies available to reach an even higher standard of code quality.

Focal 

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

### Pair Programming

All team members are to develop the product in pairs of two utilizing [Pair Programming](http://www.extremeprogramming.org/rules/pair.html). The reason for this decision is simple - pair programming means that two minds are implementing the same feature, which will lead to the produced code being more motivated and reasonable. This increases the 

### Code Reviews



### Git Workflow



### Continuous integration



### Test-driven Development



### Behavior-driven Development



### Sequenced Versioning
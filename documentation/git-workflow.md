# Git workflow

We utilize the [Git Feature Branch workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow)

In practice, it works as follows:

## FEATURE DEVELOPMENT

### Steps to Follow:

1. Start with an updated local development branch -- by checking out the dev branch and pulling changes:  
`git checkout development`
`git pull origin development`

1. Create and checkout a feature branch:  
`git checkout -b initials/fancy-branch-name`  
*Note: LP convention: Your branch name should start with your initials and then a description of your feature (as above).*

1. Do work in your feature branch, committing early and often, following [the Seven rules of a great Git commit message](#the-seven-rules-of-a-great-git-commit-message):
`git commit -m "Comment about the commit"`

1. Rebase frequently to incorporate upstream changes:  
`git fetch origin development`
`git rebase origin/development`

    - or -  
`git checkout development`
`git pull`
`git checkout initials/fancy-branch-name`
`git rebase development`

1. Optional: Perform an interactive rebase (squash) your commits before pushing the branch:  
`git fetch origin development`
`git rebase -i origin/development`

1. Once you have reviewed your changes, and verified formatting and intention, push your changes upstream to origin:  
`git push -u origin initials/fancy-branch-name`

### Get Your Code Reviewed (by creating a Pull Request)!

Your code must be reviewed by **at least one** other developer, preferably two,  and receive +1s from each of them, in order to be eligible for a Merge.

1. Create a Pull Request in github between your feature branch and development.
1. Standards for Code Review can be found here [Code Review](./code-review.md).
1. After your code passes Code Review, merge your code into **Development Branch** via the GitHub interface. Delete your branch after merging.

## PRODUCTION HOTFIX

### Steps to Follow:

1. Pull to update your local master branch:  
`git checkout master`
`git pull origin master`

1. Check out a hotfix branch.  **Note:** The hotfix branch name should start with the issue number the work is related to and a brief description. For example: 1234-big-fix  
`git checkout -b hotfix-branch-name`

1. Do work in your hotfix branch, committing early and often:  
`git commit -m "Comment about the commit"`

1. **VERY IMPORTANT:** Interactive rebase (squash) your commits before merging with development:  
This provides a single commit on master with everything in the hotfix.  
`git fetch origin master`
`git rebase -i origin/master`

1. See above for details on squashing during a rebase.  
1. Push your changes upstream to get code reviewed  
`git push -u origin hotfix-branch-name`
1. Merge your changes with master  
`git checkout master`  
`git merge --no-ff hotfix-branch-name`  
1. Back-merge your changes with development  
`git checkout development`  
`git merge --no-ff hotfix-branch-name`  
1. Push your changes upstream  
`git push origin master`  
`git push origin development`  

## PRODUCTION RELEASE

This includes everything on the development branch. If this is NOT what you want then you can cherry-pick features from the development branch. This is when having single commits per feature is very important.

1. Open a PR from `origin/development` to `origin/master` in GitHub
1. Document Rake tasks, Migrations, and changes in PR description
1. Get at least 1 more Dev to review this PR
1. When you have sign off from Dev, merge this PR
1. This will kickoff the master build, which can be monitored in CI.
1. From here, follow appropriate steps to complete the release.

### The seven rules of a great Git commit message

1. Separate subject from body with a blank line
1. Limit the subject line to 50 characters
1. Capitalize the subject line
1. Do not end the subject line with a period
1. Use the imperative mood in the subject line
1. Wrap the body at 72 characters
1. Use the body to explain what and why vs. how

### References

Much of the above is shamelessly stolen from [A Git Workflow for Agile Teams](http://reinh.com/blog/2009/03/02/a-git-workflow-for-agile-teams.html) and [A successful Git branching model](http://nvie.com/posts/a-successful-git-branching-model/).

We have simplified it and adjusted it a bit more to our liking and needs.

Tagging information has been taken from <https://git-scm.com/book/en/v2/Git-Basics-Tagging>

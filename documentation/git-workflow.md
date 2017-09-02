# Git workflow

We utilize the [Git Feature Branch workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow)  

In practice, it works as follows:  

## FEATURE DEVELOPMENT  
### Steps to Follow:    
1. Start with an updated local development branch -- by checking out the dev branch and pulling changes:    
`git checkout development`  
`git pull origin development`  
  
2. Create and checkout a feature branch:   
`git checkout -b initials/fancy-branch-name`  
*Note: LP convention: Your branch name should start with your initials and then a description of your feature (as above).*  

3. Do work in your feature branch, committing early and often:    
`git commit -m "Comment about the commit"`  

4. Rebase frequently to incorporate upstream changes:  
`git fetch origin development`  
`git rebase origin/development`    

- or -  
`git checkout development`  
`git pull`  
`git checkout initials/fancy-branch-name`  
`git rebase development`    

5. Optional: Perform an interactive rebase (squash) your commits before pushing the branch:  
`git fetch origin development`  
`git rebase -i origin/development`    
  
6. Once you have reviewed your changes, and verified formatting and intention, push your changes upstream to origin:   
`git push -u origin initials/fancy-branch-name`  

### Get Your Code Reviewed (by creating a Pull Request)!  
Your code must be reviewed by **at least one** other developer, preferably two,  and receive +1s from each of them, in order to be eligible for a Merge.  
   
1. Create a Pull Request in github between your feature branch and development.  
2. Standards for Code Review can be found here [INSERT LINK TO Code Review](#LINK).  
3. After your code passes Code Review, merge your code into **Development Branch** via the GitHub interface. Delete your branch after merging.

## PRODUCTION HOTFIX  
### Steps to Follow:  
1. Pull to update your local master branch:  
`git checkout master`  
`git pull origin master`  

2. Check out a hotfix branch.  **Note:** The hotfix branch name should start with the issue number the work is related to and a brief description. For example: 1234-big-fix    
`git checkout -b hotfix-branch-name`    

3. Do work in your hotfix branch, committing early and often:  
`git commit -m "Comment about the commit"`  

4. **VERY IMPORTANT:** Interactive rebase (squash) your commits before merging with development:  
This provides a single commit on master with everything in the hotfix.  
`git fetch origin master`
`git rebase -i origin/master`  

5. See above for details on squashing during a rebase.  
6. Push your changes upstream to get code reviewed  
`git push -u origin hotfix-branch-name`  
7. Merge your changes with master  
`git checkout master`  
`git merge --no-ff hotfix-branch-name`  
8. Back-merge your changes with development  
`git checkout development`  
`git merge --no-ff hotfix-branch-name`  
9. Push your changes upstream  
`git push origin master`  
`git push origin development`  

## PRODUCTION RELEASE  
This includes everything on the development branch. If this is NOT what you want then you can cherry-pick features from the development branch. This is when having single commits per feature is very important.  

1. Open a PR from `origin/development` to `origin/master` in GitHub
2. Document Rake tasks, Migrations, and changes in PR description  
3. Get at least 1 more Dev to review this PR  
4. When you have signoff from Dev, merge this PR  
5. This will kickoff the master build, which can be monitored in CI.  
6. From here, follow appropriate steps to complete the release.

### References  
Much of the above is shamelessly stolen from [A Git Workflow for Agile Teams](http://reinh.com/blog/2009/03/02/a-git-workflow-for-agile-teams.html) and [A successful Git branching model](http://nvie.com/posts/a-successful-git-branching-model/).  

We have simplified it and adjusted it a bit more to our liking and needs.

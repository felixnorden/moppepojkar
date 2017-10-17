# Half-time reflection of the project and application of SCRUM

[Back to top](../README.md)

During these past 4 weeks, we as a team have actively tried to apply SCRUM methodology and incorporate its different processes into our each stage of our workflow. Initially, we chose to split our main group into three smaller teams, each team consisting of four members. We then worked with establishing the teams' different problem domains to effectively kickstart the project. Every team had the vision of holding sprint planning and retrospectives weekly, but also daily stand-ups to keep all team members on track.

So far, the sprint reviews have been mainly handled by one of the teams, where the SM and PM have been stationed, while the rest have been working on their backlog as they saw fit.

## Sprint planning

Our sprint planning meetings were initially quite unorganized, which led to a few negative side effects... We quickly acknowledged the difficulty in creating small, vertical slices of larger user stories to efficiently produce value. However, we as a group have come to the conclusion that this was due to our nearly non-existent experience of SCRUM. Improvements of the planning workflow have been made, with a set team velocity budget and a timed planning structure in order to maintain focus. The result of our actions is that we see smaller, more tangible tasks on our sprint backlogs for every new sprint, which makes incorporation, testing and evaluation easier and more efficient.

### Fibonacci's sequence and Planning Poker

We decided upon not using Fibonacci's sequence when it came to scoring our user stories. The reason for this is that it is difficult to adapt to our set velocity of 100 pts/team. We could have chosen to adapt our velocity threshold to fit more accurately, but due to us having this insight later than sprint one we would have data problems with our KPI:s.

We have also taken notice of our scoring being a bit guessed and not really motivated. A possible solution for our problem would be the Fibonacci sequence, where we then could use planning poker as a tool to entice discussion and reflection. In return, we maybe would have had faster planning meetings, or more efficient meetings during the set time, yielding more accurate user story approximations and also thinner vertical tasks.

### INVEST

Another tool which could make our scrum plannings produce a better result would be to make more active use of the INVEST-criteria. We have talked about it a few times, but haven't really put our minds to it to sit down together and do the planning with the criteria at our side.

According to [Scrum Guides' sprint planning](http://www.scrumguides.org/scrum-guide.html#events-planning), our meetings should take at most 2-3 hours, which we haven't touched on as of yet. It would be interesting to allocate maybe 1.5-2 hours for planning during our next sprint and then incorporate INVEST to see if the set tasks are easier to accomplish and more value is produced at the end of the sprint.

## Daily Stand-ups

Daily stand-ups have been difficult to do because we have not established a routine for it. The problem seems to be the fact that we are not gathered enough and that the groups do not prioritize them enough. The members come and go in their different teams, and when they are finally gathered, tasks are instead prioritized. This is probably because of the fact that we do not have any full workdays to dedicate to the project. Partly due to us having lectures and partly because of some team members are working part-time. We have noticed that having these stand-ups more frequently would make the communication in and between teams easier. Therefore, this has been set as one of our three improvements of change for this week's sprint.

Another aspect for the lack of stand-ups is that some teams have mainly worked in pairs doing pair programming in the same location. This has led to all members having a better understanding of the overall state of the sprint. This is not a reason to skip the stand-ups. The important aspect of holding the meetings daily is not to only update each other, but to confirm that everybody is on the same page.

## Retrospectives

Retrospectives developed a good structure early on and we feel like this part of SCRUM is the part that is showing the best results. The methodology emerged within the group in a natural fashion, which implies that all members of the group has the same appreciation of having retrospectives. A lot of good topics about the atmosphere in the group have been brought up, which has cascaded into a lot of discussion and reflection regarding the communication in and between the smaller teams.

### The grand conflict

At an early stage of the project, a big conflict happened between two of the smaller teams. This was due to loss in communication. However, thanks to our good retrospectives and the SM's mediation between teams led to a quick and painless resolution. Instead of having a cold and dull relationship between the teams, the period after the conflict has been more than ideal and the mood has been extremely positive.

The important learning that we have taken from this is that having friction and conflicts in a group can be a good thing, because it enforces the team to cooperate and having to resolve the conflict leads to a better understanding of each other. We have also noted that these are good to have as early on as possible, as to have the team function optimally as soon as possible. Having these conflicts at later stages could instead freeze the project's state completely and the process would stagnate.

## Dynamic teamwork

During sprint three the teams we had earlier became significantly more dynamic. We needed to move members between teams and this was due to the responsibilities were to narrow at this point, and their goals began to converge into the final goal - assembling the components. This has been very positive because the exchange of knowledge between the teams increases and the [truck factor](http://www.agileadvice.com/2005/05/15/agilemanagement/truck-factor/) is higher, which entails that the team can continue to work even if members are absent. Another positive aspect of this dynamic workflow is that our members become more flexible and we have the ability to allocate resources to the tasks that need more attention.

This dynamic workflow is something that we should have adopted earlier. In the beginning, we had a team which domain did not immediately deliver value during the current sprint, while another team was understaffed and under a lot of pressure. The result of this miss was a bottleneck in the production process. If we instead had the teams cooperate during this sprint we could have eluded a lot of stress and had a more stable work environment.

Another reason for this dynamic workflow is that some components of the project are finished, unless the PO says anything else. We had, for example, a team which managed the app and communication between the MOPED and the app. When the communication package was finished, refactored and then integrated into the system, the team's domain was completed. This meant that the team's existence no longer had any meaning to the project. To enforce our agile workflow, the members instead were merged into the other teams to alleviate their workload.

## Product development

Our software has been developed through a few separate modules. The positive side to this is that it has enabled us to work in parallel from the very beginning, and this has proven to be very effective as we have managed to produce a lot of functions in every domain of the project. The negative side of this is that some functionality has been hard to test and the vertical slices of tasks have been larger and more horizontal than what could be optimal.

Our reason behind this "waterfall" approach was that we had a very steep learning curve of the codebase, and we wanted to get over the threshold as soon as possible. Thanks to this decision, we have now developed a functioning platform. Having this platform, and our teams cooperating dynamically, we now have an easier time slicing tasks vertically and creating more tangible tasks.

## Problems we hit and our response

### Servers are not the solution

During the first sprint we dedicated a lot of time to set up the server. After having seen another team utilizing already integrated python scripts on the MOPED and having it act somewhat autonomously, we concluded that an alternative solution could be useful if we could not start the server. As we did not manage to produce value according to our planning, we chose to take our alternative route. In hindsight, we are very appreciative for making this decision as early as we did. Due to this decision, our product is in a good state compared to the teams' product, and we have been able to share our alternative solution to help other teams, which has been very fun!

### MOPEDs are fragile

On several occasions we have had to work with MOPEDs which had some form of malfunctioning hardware. We have been able to try our software on some occasions, as long as the malfunctioning hardware was not involved. At other times, we have been lucky to have good relationships with a few other groups, who has lent us their MOPED for a limited time, which has helped out a lot. However, this has not always worked out and we have had to take extreme measures to continue our development. E.g. during sprint 4, our CAN-network stopped functioning completely and we had to make use of an Arduino to hook up our TCU, which then acts as both the VCU and SCU. At the moment, we see this as a temporary solution. However, if it turns out to get rid of some problems we currently have, we may go with this route to make sure to deliver our platooning feature in time.

The fragility and, maybe somewhat irresponsible, usage of them has led to a lot of bottlenecks and throwbacks in our schedule during different sprints. As this seems to have become a recurring problem, it may be useful to take this aspect into account during our sprint plannings. This way we will not be subjugated to as much stress and pressure of not delivering value when we are not at fault for not being able to produce more of it. 

## The backside of DoD

One thing that strains the group is that we have not been able to confirm our produced value with the PO, due to his absence during week 2. We have had a lot of user stories which have been as good as done, or that have been waiting for code reviews and testing. Our Definition of Done is quite strict and has at some points made it difficult for us to deploy our product and acquire our produced value. It has a lot of steps before the software even reaches the MOPED. The upside to this is that our functionality often is confirmed to work before it reaches the MOPED and the manual tests are therefore shorter. However, the downside to this is that the whole process is pretty long and arduous, which easily disheartens the group if something goes wrong. It has also been a bit disheartening for us as we felt like we had not produced any sort product value until sprint 3, where we deployed our first version of the ACC.
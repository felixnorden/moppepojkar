# Sprint Planning

This document works as a basic template for the weekly Sprint Planning, and holds a few guidelines as to make the Planning go more smoothly and to facilitate the estimations of a reasonably set Sprint Goal.

The plan is created by the collaborative work of the **entire Scrum Team**.

Sprint Planning is time-boxed to a maximum of 2-3 hours for a week-long Sprint. The Scrum Master ensures that the event takes place and that attendants understand its purpose. The Scrum Master teaches the Scrum Team to keep it within the time-box.

Sprint Planning answers the following:

- What can be delivered in the Increment resulting from the upcoming Sprint?
- How will the work needed to deliver the Increment be achieved?

## Sprint Goal

The Sprint Goal is an objective set for the Sprint that can be met through the implementation of Product Backlog. It provides guidance to the Development Team on why it is building the Increment. It is created during the Sprint Planning meeting. The Sprint Goal gives the Development Team some flexibility regarding the functionality implemented within the Sprint. The selected Product Backlog items deliver one coherent function, which can be the Sprint Goal. The Sprint Goal can be any other coherence that causes the Development Team to work together rather than on separate initiatives.

As the Development Team works, it keeps the Sprint Goal in mind. In order to satisfy the Sprint Goal, it implements the functionality and technology. If the work turns out to be different than the Development Team expected, they collaborate with the Product Owner to negotiate the scope of Sprint Backlog within the Sprint.

### Product Backlog items

This section should contain a list of chosen Product Backlog items that are estimated to be "Done" by the end of the sprint.

- Make the MOPED act without user input (150 p)
- Keep distance to MOPED in front (150 p)
- (Start in a "Safe" mode (100 p))

### Final Sprint Goal

This section will contain the Sprint Goal for the sprint, which should describe:

- the chosen Product Backlog items, i.e **What**
- chosen items' relationship to each other
- implementation order to mitigate the risk of not producing estimated value
- rationale as to **How** and **Why** this will be the Sprint Goal

#### How

The primary goal of this sprint will be to set up the MOPED in a configuration where it will be able to work autonomously using any, already available, plugin the work will be split up among three smaller teams:

- Architecture - Whom will manage the overall structure of the MOPED's software, and how the different modules interact with each other when the it's running.
- Logic - Whom will manage the logic and calculations for the plugin that the team will develop.
- Communication - Whom will manage the interaction between the controller/phone and the MOPED for switching states between manual and autonomous.

##### Architecture

- "Set car to move autonomously"
- "Allow car to keep a safe distance obstacles in front"

##### Logic

- "Allow car to keep a safe distance obstacles in front"

##### Communication

- "Switch between different driving modes from GUI"

### Why

This goal has been set with taking the overall overhead from setting things up into account. With each of the smaller teams managing their own layer of the vertically sliced user stories, they will become more familiar with its already existing eco system and therefore be able to work in other, more diversified teams in future sprints.

Even though learning a new codebase has a steep learning curve, we still want to produce some sort of value for the PO. Therefore, we at least plan to make the MOPED act autonomously, without any interaction of a user. 

---

## Topic One: What can be done this Sprint?

This section is use for deciding on what can be accomplished during the current sprint. The set goal should deliver some form of value towards the end product. The entire Scrum Team collaborates on understanding the work of the Sprint.

The input to this meeting is:

- the Product Backlog
- the latest product increment
- projected capacity of the Development Team during the Sprint
- past performance of the Development Team

The number of items selected from the Product Backlog for the Sprint is solely up to the Development Team. Only the Development Team can assess what it can accomplish over the upcoming Sprint.

After the Development Team forecasts the Product Backlog items it will deliver in the Sprint, the Scrum Team crafts a [Sprint Goal](#sprint-goal). The Sprint Goal is an objective that will be met within the Sprint through the implementation of the Product Backlog, and it provides guidance to the Development Team on why it is building the Increment.

### Last Product Increment

This section should describe the last Product Increment. It could be added manually below, or linked directly to the specific document.

### Capacity of Dev Team

This section should contain metrics/useful data that the SM can utilize to help the team decide on a valid and reasonable Sprint Goal. The data could be added manually below, or linked directly to the documents where it is stored.

### Past Performance of Dev Team

This section should contain the past performances of the dev team. Important to note is that it should be **objective** and only focus on the actual produced value. Information that is placed here is for helping the team to make more precise estimations and for the SM to adapt/modify the current KPIs. The information could be added manually below, or linked directly to the documents where it is stored.

---

## Topic Two: How will the chosen work get done?

Having set the Sprint Goal and selected the Product Backlog items for the Sprint, the Development Team decides how it will build this functionality into a “Done” product Increment during the Sprint. The Product Backlog items selected for this Sprint plus the plan for delivering them is called the Sprint Backlog.

The Development Team usually starts by designing the system and the work needed to convert the Product Backlog into a working product Increment. Work may be of varying size, or estimated effort. However, enough work is planned during Sprint Planning for the Development Team to forecast what it believes it can do in the upcoming Sprint. Work planned for the first days of the Sprint by the Development Team is decomposed by the end of this meeting, often to units of one day or less. The Development Team self-organizes to undertake the work in the Sprint Backlog, both during Sprint Planning and as needed throughout the Sprint.

The Product Owner can help to clarify the selected Product Backlog items and make trade-offs. If the Development Team determines it has too much or too little work, it may renegotiate the selected Product Backlog items with the Product Owner. The Development Team may also invite other people to attend in order to provide technical or domain advice.

By the end of the Sprint Planning, the Development Team should be able to explain to the Product Owner and Scrum Master how it intends to work as a self-organizing team to accomplish the Sprint Goal and create the anticipated Increment.

---

## Reference

This material is copied from [Scrum Guides' Sprint Planning](http://www.scrumguides.org/scrum-guide.html#events-planning) and is modified to fit the scope of this project.
# Product Backlog

This document describes the key features that the Product Owner has in mind, in conjunction with the Teams.

**Check product spec on github repo**

## Epics

- Adaptive cruise control (longitudinal management)
- Platooning feature (longitudinal & lateral management)

## Themes

- Autonomy
- Maneuverability
- MOPEDs should be able to work together, individual functionality isn't in scope
- Keep a common distance between each other.


## User Stories

### Adaptive cruise control

- Adapt velocity **(Main focus)**
- Take obstacles into account **(Not of utmost importance)**
- Maneuverability is already implemented, not necessary to change **(YAGNI)**
- Turn on/off adaptive cruise from the RC/driver seat. **(Important)**

### Platooning

- Make the MOPED follow another MOPED
- Safety first! Moving people will be on the test area. **(Important!)**
- First vehicle will be maneuvered manually, the rest will follow
- Keep track of hindrances and avoid collisions **(Not of utmost importance)**
- Allow switching of roles, from follower to lead role and vice versa:
    - All cars should be able to do these **(Not of utmost importance)**
    - Positive if column can be split somewhere in the middle when cars are to go to different locations **(Not of utmost importance)**
    - Turn on/off follow mode from the RC/driver seat **(Necessary for demo)**
- Decision making regarding the following vehicles:
    - Will steer away or stop when a collision cannot be steered off **(Not of utmost importance)**

## End Product

The end goal is to produce column based driving demo, where one main vehicle is driven manually and the rest follow in line.

This solution will help with sustainability and human safety for the future.

Cars will converse with each other in SoS, but have to be functioning independently and can make decisions on their own. The demonstration will be made using all the different MOPEDs, that each utilize their own software, but can cooperate together.


## Check later on

- Preferred distance between MOPEDs, based on sensors
- Absolute start position using coordinates or something.
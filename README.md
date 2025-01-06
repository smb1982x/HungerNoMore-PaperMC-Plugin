# HungerNoMore
_A PaperMC Minecraft Java Edition Plugin that disables the Hunger mechanic. Food now heals your players directly!_

## Overview
HungerNoMore is a lightweight plugin for PaperMC servers that removes Minecraft's default hunger mechanic and provides direct healing upon consuming food. Whether you're running a PvE survival world or a minigame-focused server, **HungerNoMore** simplifies game balance by keeping food straightforward—eat to heal, no more hunger bar management!

## Features
- **No Hunger**: Keeps the player's hunger bar permanently full (configurable).
- **Healing Foods**: Consuming food directly restores health rather than hunger.
- **Harmful Foods**: Certain “harmful” items can damage the player instead (e.g., rotten flesh).
- **Permissions**: Allows players with specific permissions to bypass plugin restrictions.
- **Customizable**: You can modify healing amounts, messages, and other core behaviors in the config.

## Requirements
- [PaperMC](https://papermc.io/) **(Recommended)** or [Spigot](https://www.spigotmc.org/) 
- Java 17 or later (version depends on your Paper/Spigot version requirements)
- Basic knowledge of uploading and managing plugins on a Paper/Spigot server

## Installation
1. **Download** the latest `HungerNoMore` plugin jar from the [Releases](#).
2. **Stop** your Minecraft server if it is running.
3. Place the `HungerNoMore.jar` file into your server’s `plugins` folder.
4. **Start** your server to generate the default config.

## Configuration
Once the server starts, a default config file (`config.yml`) is generated in the plugin’s folder.  
Open `config.yml` to modify:

| Setting                 | Default | Description                                                                                                       |
|-------------------------|---------|-------------------------------------------------------------------------------------------------------------------|
| `keep-full`             | true    | If true, players’ hunger bars are always frozen at 95%.                                                           |
| `disable-natural-regen` | true    | If true, players will not naturally regenerate health from hunger bar.                                            |
| `hungerhealValue`       | 4.0     | Base amount of health restored upon eating foods in the “healing” category. (4.0 = Cooked Beef restores 2 Hearts) |
| `damageValue`           | 2.0     | Base amount of damage applied upon consuming harmful foods.                                                       |
| `customPercent`         | 1.0     | Multiplier for both damage and healing calculations.                                                              |
| `messages`              | Various | Customize the messages sent to players when they heal or take damage from certain foods.                          |
 
## Usage
1. **Join the server** and observe that your hunger bar is full at all times (if `keepHungerFull` is true).
2. **Consume** a piece of food. Instead of replenishing hunger, it restores health (or deals damage, if it’s harmful).
3. **Adjust** heal/damage values or messages in `config.yml` to suit your server’s gameplay preferences.

## Permissions
- **`hungernomore.bypass`**: Players with this permission are unaffected by HungerNoMore’s mechanics (no healing/damage on food consumption).

## Contributing
Issues and pull requests are welcome! 
1. Fork the project.
2. Create a new branch (e.g., `fix-bug` or `feature-request`).
3. Commit changes and open a merge request.  
For major changes, please open an issue first to discuss what you’d like to change.

## License
This project is licensed under the [MIT License](./LICENSE).

---

_Enjoy the simplified gameplay where “food” truly means “health!” If you have any questions or concerns, please open an issue or contact us on our [GitLab repository](#)._

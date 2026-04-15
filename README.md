# Install tools on Windows

## Terminal Setup

1. Install Window Terminal via Microsoft Store
2. Install [Git](https://git-scm.com/install/windows)
3. Install PowerShellGet

```bash
Install-Module -Name PowerShellGet -Scope CurrentUser -Force
```

4. Install [post-git](https://github.com/dahlbyk/posh-git/blob/v0/README.md)
5. Install [PSReadLine](https://github.com/PowerShell/PSReadLine)

```bash
Install-Module PSReadLine -Repository PSGallery -Scope CurrentUser -AllowPrerelease -Force
```

6. Intall Oh-my-post 

    1. via PowerShell

```bash
Set-ExecutionPolicy Bypass -Scope Process -Force; Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://ohmyposh.dev/install.ps1'))
```

    2. via winget

```bash
winget install JanDeDobbeleer.OhMyPosh --source winget
```

7. Install Oh
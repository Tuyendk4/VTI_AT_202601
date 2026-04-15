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

## Git tutorial

1. Clone remote repository to local machine (Note: do only one time)

```bash
git clone https://github.com/Tuyendk4/VTI_AT_202601.git. //You are still local repository
```

2. Create new branch on local machine (1 branch = 1 task of you in the project)

```bash
git checkout -b <branch_name>  // create and move to <branch_name> - move to working dir
```

means:

```bash
git branch <branch_name>  //create new branch
git checkout <branch_name> //move to <branch_name>
```

3. Add/edit/update/delete files
4. Add all changed files to git

```bash
git add -A  //Add all changed files to git - move to staging (indexing)
```

5. Add a short message to explain what your task is to the branch

```bash
git commit -m "<your task>" //move to local repository
```

6. Push your branch on local machine to remote repository

```bash
git push origin <branch_name> //You are still local repository
```

7. Team leader review and merge the branch to other branch (ex: main)
8. Team member pull new changes from main branch of remote repository to main branch of local machine

```bash
git checkout main
git pull origin main
```

Back to Step 2 to continue your work



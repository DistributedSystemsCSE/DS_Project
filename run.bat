@echo off
SET arg1=%1
IF [%arg1%] == [] (
   FOR /L %%A IN (1,1,3) DO (
		start /B java -cp DS_Project.jar ds_project.DS_Project
	)
) ELSE (
    FOR /L %%A IN (1,1,%arg1%) DO (
		start /B java -cp DS_Project.jar ds_project.DS_Project
	)
)
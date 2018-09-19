# Intragenic-miR-ExploreR-UI
This is meant to provide a graphical user interface to Surajit Bhattacharya's program in R.

The program in R is a tool for biologists that provides a score to be used as a predictor miRNA targets.
https://bioconductor.org/packages/devel/bioc/html/IntramiRExploreR.html

It is- obviously- slow, underoptimized, and unfinished. However, it is functional. Some miRs need to be updated as per advances in 
genomics. There is also a major issue with RCaller whereby the visualization function executes correctly but exits the opened window 
upon completion that I made various attempts at overcoming through use of threads. 

I hope to eventually recreate this using R exclusively to overcome the visualization issue.

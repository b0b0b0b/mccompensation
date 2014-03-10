library(ggplot2)
library(scales)
df <- read.delim(paste(Sys.getenv("HOME"), "monte-carlo.txt", sep='/'), header=T, sep='\t')
summary(df)
dfs <- read.delim(paste(Sys.getenv("HOME"), "monte-carlo-stock.txt", sep='/'), header=T, sep='\t')
summary(dfs)
dft <- read.delim(paste(Sys.getenv("HOME"), "monte-carlo-total.txt", sep='/'), header=T, sep='\t')
summary(dft)
dfa <- read.delim(paste(Sys.getenv("HOME"), "monte-carlo-annual.txt", sep='/'), header=T, sep='\t')
summary(dfa)

# total ecdf
ggplot(dft, aes(x=value, color=who)) + stat_ecdf() +
  scale_y_continuous(labels=percent)+
  scale_x_continuous(labels=comma)

# stacked ecdf's annual
ggplot(dfa, aes(x=value, color=who)) + stat_ecdf() +
  facet_grid(when ~ .) +
  scale_y_continuous(labels=percent)+
  scale_x_continuous(labels=comma)

# stacked ecdf's quarterly
ggplot(df, aes(x=value, color=who)) + stat_ecdf() +
  facet_grid(when ~ .) +
  scale_y_continuous(labels=percent)+
  scale_x_continuous(labels=comma)

# stacked annual income histograms
ggplot(dfa, aes(x=value, fill=who)) +
  geom_histogram(binwidth=5000) +
  facet_grid(who ~ when, scale="free_y") +
  scale_x_continuous(labels = comma) +
  theme(axis.text.y = element_blank(), axis.ticks.y = element_blank()) +
  scale_y_continuous("")

# stacked quarterly income histograms
ggplot(df, aes(x=value, fill=who)) +
  geom_histogram(binwidth=5000) +
  facet_wrap(~ when, nrow=4, scale="free_y") +
  scale_x_continuous(labels = comma) +
  theme(axis.text.y = element_blank(), axis.ticks.y = element_blank()) +
  scale_y_continuous("")

# quarterly share price histograms
ggplot(dfs, aes(x=value, fill=who, xmin=0)) +
  geom_histogram() +
  facet_grid(when ~ who, scales="free") +
  scale_x_continuous(labels = comma, min=0) +
  theme(axis.text.y = element_blank(), axis.ticks.y = element_blank()) +
  scale_y_continuous("")


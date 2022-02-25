################################## IMPORTS ##################################
import pandas as pd
import numpy as np

from ItemKNNCFRecommender import ItemKNNCFRecommender

################################# READ DATA #################################

FSM = pd.read_csv(filepath_or_buffer="data_ FSM.csv",
                           sep=',',
                           names = ["farm_id", "product_id", "score"],
                           header=0,
                           dtype={'row': np.int32, 'col': np.int32, 'data': np.float64})

####################### ISTANTIATE AND FIT THE CF #######################

recommender = ItemKNNCFRecommender(FSM)
recommender.fit()

################################ PRODUCE CSV ################################

f = open("submission.csv", "w+")
f.write("farm_id,product_id\n")
farm_id = 729445
recommended_items = recommender.recommend(farm_id, cutoff=5, remove_seen_flag=True)
well_formatted = " ".join([str(x) for x in recommended_items])
f.write(f"{farm_id}, {well_formatted}\n")
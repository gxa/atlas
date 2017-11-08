-- Microarray differential analytics
DROP TABLE MICROARRAY_DIFF_ANALYTICS CASCADE;
CREATE TABLE MICROARRAY_DIFF_ANALYTICS (
    DESIGNELEMENT VARCHAR(255) NOT NULL
  , EXPERIMENT VARCHAR(255) NOT NULL
  , ARRAYDESIGN  VARCHAR(255) NOT NULL
  , CONTRASTID VARCHAR(255) NOT NULL
  , PVAL FLOAT(53) NOT NULL
  , LOG2FOLD FLOAT(53)
  , TSTAT FLOAT(53)
  , PRIMARY KEY (DESIGNELEMENT, EXPERIMENT, CONTRASTID, PVAL)
);

CREATE INDEX MA_DIFF_ANALYTICS_IDX ON MICROARRAY_DIFF_ANALYTICS (EXPERIMENT, CONTRASTID);

-- RNA-seq differential analytics
DROP TABLE RNASEQ_DIFF_ANALYTICS CASCADE;
CREATE TABLE RNASEQ_DIFF_ANALYTICS (
    IDENTIFIER VARCHAR(255) NOT NULL
  , EXPERIMENT VARCHAR(255) NOT NULL
  , CONTRASTID VARCHAR(255) NOT NULL
  , PVAL FLOAT(53) NOT NULL
  , LOG2FOLD FLOAT(53)
  , PRIMARY KEY (IDENTIFIER, EXPERIMENT, CONTRASTID, PVAL)
);

CREATE INDEX RNASEQ_DIFF_ANALYTICS_IDX ON RNASEQ_DIFF_ANALYTICS (EXPERIMENT, CONTRASTID);

-- RNA-seq baseline analytics
DROP TABLE RNASEQ_BSLN_EXPRESSIONS CASCADE;
CREATE TABLE RNASEQ_BSLN_EXPRESSIONS (
    IDENTIFIER VARCHAR(255) NOT NULL
  , EXPERIMENT VARCHAR(255) NOT NULL
  , ASSAYGROUPID VARCHAR(255) NOT NULL
  , EXPRESSION FLOAT(53) NOT NULL
  , PRIMARY KEY (IDENTIFIER, EXPERIMENT, ASSAYGROUPID, EXPRESSION)
);

CREATE INDEX RSQ_BSLN_EXPRESSIONS_IDX ON RNASEQ_BSLN_EXPRESSIONS (IDENTIFIER, EXPERIMENT, ASSAYGROUPID);

-- miRBase and Ensembl gene names
DROP TABLE BIOENTITY_NAME CASCADE;
CREATE TABLE BIOENTITY_NAME (
    identifier VARCHAR(255) NOT NULL
    , organismid NUMERIC(22,0) NOT NULL
    , type VARCHAR(50) NOT NULL
    , name VARCHAR(255)
    , PRIMARY KEY (IDENTIFIER, ORGANISMID, TYPE)
);

-- miRBase and Ensembl design element to gene mappings
DROP TABLE DESIGNELEMENT_MAPPING CASCADE;
CREATE TABLE DESIGNELEMENT_MAPPING (
    designelement VARCHAR(255) NOT NULL
    , identifier VARCHAR(255) NOT NULL
    , type VARCHAR(50) NOT NULL
    , arraydesign  VARCHAR(255) NOT NULL
    , PRIMARY KEY (DESIGNELEMENT, IDENTIFIER, ARRAYDESIGN)    
);


-- Reference table mapping array design accession in DESIGNELEMENT_MAPPING to their user-friendly names
DROP TABLE ARRAYDESIGN CASCADE;
CREATE TABLE ARRAYDESIGN (
    accession  VARCHAR(255) NOT NULL
    , name  VARCHAR(255) NOT NULL
    , PRIMARY KEY (ACCESSION)        
);

-- Index to support FK_ARRAYDESIGN on DESIGNELEMENT_MAPPING
CREATE INDEX designelement_mapping_fk ON designelement_mapping USING btree (arraydesign);
ALTER TABLE designelement_mapping
  ADD CONSTRAINT fk_dem_arraydesign FOREIGN KEY (arraydesign) REFERENCES arraydesign(accession) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;

-- Index to support FK_ARRAYDESIGN on MICROARRAY_DIFF_ANALYTICS
CREATE INDEX microarray_diff_analytics_fk ON MICROARRAY_DIFF_ANALYTICS USING btree (arraydesign);
ALTER TABLE MICROARRAY_DIFF_ANALYTICS
  ADD CONSTRAINT fk_dem_arraydesign FOREIGN KEY (ARRAYDESIGN) REFERENCES arraydesign(ACCESSION) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE;

-- Reference table for all bioentity organisms (across Ensembl and miRBase)
DROP TABLE BIOENTITY_ORGANISM CASCADE;
CREATE TABLE BIOENTITY_ORGANISM (
    organismid NUMERIC(22,0) NOT NULL
    , name VARCHAR(255) NOT NULL
    , PRIMARY KEY (ORGANISMID) 
);

-- Experiment table
ALTER TABLE EXPERIMENT_ORGANISM DROP CONSTRAINT FK_EXPERIMENT_ORGANISM;
DROP TABLE EXPERIMENT CASCADE;
CREATE TABLE EXPERIMENT(
    ACCESSION VARCHAR(255) NOT NULL
    , TYPE VARCHAR(50) NOT NULL
    , ACCESS_KEY CHAR(36) NOT NULL
    , PRIVATE VARCHAR(1) DEFAULT 'T'
    , LAST_UPDATE DATE DEFAULT current_date
    , PUBMED_IDS VARCHAR(255)
    , TITLE VARCHAR(500)
    , PRIMARY KEY (ACCESSION)     
);

CREATE OR REPLACE VIEW PUBLIC_EXPERIMENT AS
SELECT ACCESSION, type, LAST_UPDATE
FROM EXPERIMENT WHERE PRIVATE='F';

-- Species table
DROP TABLE EXPERIMENT_ORGANISM CASCADE;
CREATE TABLE EXPERIMENT_ORGANISM(
    ORGANISM VARCHAR(255) NOT NULL
    , EXPERIMENT VARCHAR(255) NOT NULL
    , PRIMARY KEY (EXPERIMENT) 
);

CREATE OR REPLACE VIEW VW_EXPERIMENT_ORGANISM
AS
select organism, experiment, split_part(ORGANISM, ' ', 1) || ' ' || split_part(ORGANISM, ' ', 2) as BIOENTITY_ORGANISM
from EXPERIMENT_ORGANISM;

-- Table for storing sample attribute-value mappings to ontology
DROP TABLE ONTOLOGY_MAPPINGS CASCADE;
CREATE TABLE ONTOLOGY_MAPPINGS (
    PROPERTY_NAME VARCHAR(255) NOT NULL
    , PROPERTY_VALUE VARCHAR(255) NOT NULL
    , ONTOLOGY_URI VARCHAR(255) NOT NULL
    , DATE_UPDATE TIMESTAMP NOT NULL
    , PRIMARY KEY (PROPERTY_NAME, PROPERTY_VALUE) 
);

-- Loading table for ONTOLOGY_MAPPINGS
DROP TABLE ONTOLOGY_MAPPINGS_LOADING CASCADE;
CREATE TABLE ONTOLOGY_MAPPINGS_LOADING (
    PROPERTY_NAME VARCHAR(255) NOT NULL,
    PROPERTY_VALUE VARCHAR(255) NOT NULL,
    ONTOLOGY_URI VARCHAR(255) NOT NULL
);

-- Table for storing co-expressed genes in baseline experiments
DROP TABLE RNASEQ_BSLN_CE_PROFILES CASCADE;
CREATE TABLE RNASEQ_BSLN_CE_PROFILES (
  EXPERIMENT VARCHAR(255) NOT NULL
  , IDENTIFIER VARCHAR(255) NOT NULL
  , CE_IDENTIFIERS TEXT NOT NULL
  , PRIMARY KEY (EXPERIMENT, IDENTIFIER)
);

-- Data processing in-progress flag table - used for data production in Atlas - no dependency on it exists in the Web services code
DROP TABLE ATLAS_JOBS CASCADE;
CREATE TABLE ATLAS_JOBS(
    DATE_STARTED DATE DEFAULT current_date,
    JOBTYPE VARCHAR(255) NOT NULL,
    JOBOBJECT VARCHAR(255) NULL -- Experiment accession or organism
);

-- Table used by Atlas production code to maintain Atlas eligibility checking status
DROP TABLE ATLAS_ELIGIBILITY CASCADE;
CREATE TABLE ATLAS_ELIGIBILITY(
    DATE_LAST_UPDATED DATE DEFAULT current_date, -- Date Atlas eligibiliy last run
    AE2_ACC VARCHAR(255) NOT NULL, -- AE2 accession
    ENA_STUDY_ID VARCHAR(255) NULL, -- ENA study id
    GEO_ACC VARCHAR(255) NULL, -- GEO accession
    STATUS VARCHAR(255) NULL, -- Corresponds to values in 'Atlas status' column in http://plantain:3002/
    COMMENT VARCHAR(255) NULL -- Any error messages in the case of Atlas eligibility failure (or unexpected system-level exceptions while running the eligibility code)
);

DROP TABLE REFERENCE_IDS CASCADE;
CREATE TABLE REFERENCE_IDS (
    ACC_PREFIX VARCHAR(15) NOT NULL, -- Prefix for the mint-able accession, e.g. 'ERAD'
    MAX_ID INTEGER NOT NULL -- Maximum number already allocated
);

-- Initialise the table (based on the output of the following query in AE2 PROD: select max(to_number(replace(acc,'E-ERAD-',''))) from study where acc like 'E-ERAD-%'
insert into REFERENCE_IDS values ( 'E-ERAD', 610 );

-- Stored function to return the next available accession, for the prefix p_prefix
CREATE OR REPLACE FUNCTION mint_accession(p_prefix char(15), OUT p_accession varchar(255))
AS $$
BEGIN
UPDATE reference_ids SET max_id = max_id + 1 where acc_prefix = p_prefix;
SELECT concat(acc_prefix,'-',max_id) into p_accession from reference_ids where acc_prefix = p_prefix;
END; $$
LANGUAGE plpgsql;

-- To call the above function do:
-- \set AUTOCOMMIT off
-- select p_accession from mint_accession('E-ERAD');
-- commit;

-- Differential analytics across all types of experiments, with additional gene name and organism information, restricted to FDR<0.05 and log2fold>=1
-- Used by the UI
DROP MATERIALIZED VIEW VW_DIFFANALYTICS CASCADE;
CREATE MATERIALIZED VIEW VW_DIFFANALYTICS
AS
select IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT
from (
select dem.IDENTIFIER, bn.NAME AS NAME, o.NAME AS ORGANISM, mda.EXPERIMENT, mda.CONTRASTID, mda.PVAL, mda.LOG2FOLD, mda.TSTAT
,rank() over(partition by mda.EXPERIMENT, mda.CONTRASTID, dem.IDENTIFIER order by(abs(mda.LOG2FOLD)) desc) as lfrank
from MICROARRAY_DIFF_ANALYTICS mda
join DESIGNELEMENT_MAPPING dem on mda.designelement=dem.designelement and mda.arraydesign = dem.arraydesign
join BIOENTITY_NAME bn on dem.identifier=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
where abs(mda.LOG2FOLD) >= 1
and mda.PVAL < 0.05
) sq where lfrank = 1
union all
select rda.IDENTIFIER, bn.NAME AS NAME, o.name AS ORGANISM, rda.EXPERIMENT, rda.CONTRASTID, rda.PVAL, rda.LOG2FOLD, null
from RNASEQ_DIFF_ANALYTICS rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join VW_EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment
where abs(rda.LOG2FOLD) >= 1
and rda.PVAL < 0.05
WITH NO DATA;

refresh materialized view VW_DIFFANALYTICS WITH DATA;

-- Differential analytics across all types of experiments, with additional gene name and organism information, restricted to FDR<0.05 only
-- Used to generate a data dump at Atlas release time
DROP MATERIALIZED VIEW VW_DIFFANALYTICS_DUMP CASCADE;
CREATE MATERIALIZED VIEW VW_DIFFANALYTICS_DUMP
AS
select IDENTIFIER, NAME, ORGANISM, EXPERIMENT, CONTRASTID, PVAL, LOG2FOLD, TSTAT
from (
select dem.IDENTIFIER, bn.NAME AS NAME, o.NAME AS ORGANISM, mda.EXPERIMENT, mda.CONTRASTID, mda.PVAL, mda.LOG2FOLD, mda.TSTAT
,rank() over(partition by mda.EXPERIMENT, mda.CONTRASTID, dem.IDENTIFIER order by(abs(mda.LOG2FOLD)) desc) as lfrank
from MICROARRAY_DIFF_ANALYTICS mda
join DESIGNELEMENT_MAPPING dem on mda.designelement=dem.designelement and mda.arraydesign = dem.arraydesign
join BIOENTITY_NAME bn on dem.identifier=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
and mda.PVAL < 0.05
) sq where lfrank = 1
union all
select rda.IDENTIFIER, bn.NAME AS NAME, o.name AS ORGANISM, rda.EXPERIMENT, rda.CONTRASTID, rda.PVAL, rda.LOG2FOLD, null
from RNASEQ_DIFF_ANALYTICS rda
join BIOENTITY_NAME bn on rda.IDENTIFIER=bn.identifier
join BIOENTITY_ORGANISM o on bn.organismid = o.organismid
join VW_EXPERIMENT_ORGANISM eo on o.name = eo.bioentity_organism and eo.experiment = rda.experiment
and rda.PVAL < 0.05
WITH NO DATA;

refresh materialized view VW_DIFFANALYTICS_DUMP WITH DATA;

exit;

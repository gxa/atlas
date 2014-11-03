package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.NumberFormat;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class DifferentialProfilesViewModelBuilder {

    private final ColourGradient colourGradient;
    private final PValueFormatter pValueFormatter;
    private final NumberFormat format2Dp = NumberFormat.getNumberInstance();
    private final FoldChangeRounder foldChangeRounder = new FoldChangeRounder();

    @Inject
    public DifferentialProfilesViewModelBuilder(ColourGradient colourGradient, PValueFormatter pValueFormatter) {
        this.colourGradient = colourGradient;
        this.pValueFormatter = pValueFormatter;

        format2Dp.setGroupingUsed(false);
        format2Dp.setMaximumFractionDigits(2);
    }

    public DifferentialProfilesViewModel build(DifferentialProfilesList<? extends DifferentialProfile<? extends DifferentialExpression>> diffProfiles, Set<Contrast> orderedContrasts) {
        DifferentialProfileRowViewModel[] genes = buildGenes(diffProfiles, orderedContrasts);

        return new DifferentialProfilesViewModel(diffProfiles.getMinUpRegulatedExpressionLevel(), diffProfiles.getMaxUpRegulatedExpressionLevel(), diffProfiles.getMinDownRegulatedExpressionLevel(), diffProfiles.getMaxDownRegulatedExpressionLevel(), diffProfiles.getTotalResultCount(), genes);
    }

    public DifferentialProfileRowViewModel[] buildGenes(DifferentialProfilesList<? extends DifferentialProfile<? extends DifferentialExpression>> profiles, Set<Contrast> orderedContrasts) {
        return build(profiles, orderedContrasts, profiles.getMinUpRegulatedExpressionLevel(), profiles.getMaxUpRegulatedExpressionLevel(), profiles.getMinDownRegulatedExpressionLevel(), profiles.getMaxDownRegulatedExpressionLevel());
    }

    private DifferentialProfileRowViewModel[] build(List<? extends DifferentialProfile<? extends DifferentialExpression>> profiles, Set<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        DifferentialProfileRowViewModel[] viewModels = new DifferentialProfileRowViewModel[profiles.size()];

        int i = 0;
        for (DifferentialProfile<? extends DifferentialExpression> profile : profiles) {
            DifferentialProfileRowViewModel profileViewModel = build(profile, orderedContrasts, minUpLevel, maxUpLevel, minDownLevel, maxDownLevel);
            viewModels[i++] = profileViewModel;
        }

        return viewModels;
    }

    private DifferentialProfileRowViewModel build(DifferentialProfile<? extends DifferentialExpression> profile, Set<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        String geneId = profile.getId();
        String geneName = profile.getName();
        String designElement = (profile instanceof MicroarrayProfile) ? ((MicroarrayProfile)profile).getDesignElementName() : null;
        DifferentialExpressionViewModel[] expressions = buildExpressions(profile, orderedContrasts, minUpLevel, maxUpLevel, minDownLevel, maxDownLevel);
        return new DifferentialProfileRowViewModel(geneId, geneName, designElement, expressions);
    }

    private DifferentialExpressionViewModel[] buildExpressions(DifferentialProfile<? extends DifferentialExpression> profile, Set<Contrast> orderedContrasts, double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel) {
        DifferentialExpressionViewModel[] expressionViewModels = new DifferentialExpressionViewModel[orderedContrasts.size()];

        int i = 0;
        for (Contrast contrast : orderedContrasts) {
            String contrastName = contrast.getDisplayName();
            DifferentialExpression expression = profile.getExpression(contrast);

            String foldChange = (expression == null) ? null : foldChangeRounder.format(expression.getFoldChange());
            String color = (expression == null) ? null : expression.isOverExpressed() ? colourGradient.getGradientColour(expression.getFoldChange(), minUpLevel, maxUpLevel, "pink", "red") : colourGradient.getGradientColour(expression.getFoldChange(), minDownLevel, maxDownLevel, "lightGray", "blue");
            String pValue = (expression == null) ? null : pValueFormatter.formatPValueAsScientificNotation(expression.getPValue());
            String tStat = !(expression instanceof MicroarrayExpression) ? null : format2Dp.format(((MicroarrayExpression) expression).getTstatistic());

            DifferentialExpressionViewModel expressionViewModel = new DifferentialExpressionViewModel(contrastName, color, foldChange, pValue, tStat);
            expressionViewModels[i++] = expressionViewModel;
        }

        return expressionViewModels;
    }

}

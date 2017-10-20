package cn.kanyun.cpa.model.dto.itempool;

import cn.kanyun.cpa.model.entity.itempool.CpaOption;
import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;
import cn.kanyun.cpa.model.entity.itempool.CpaSolution;

import java.util.List;

public class ItemForm {
    private CpaRepertory cpaRepertory;
    private List<CpaOption> cpaOptions;
    private CpaSolution cpaSolution;

    public ItemForm() {
    }

    public ItemForm(CpaRepertory cpaRepertory, List<CpaOption> cpaOptions, CpaSolution cpaSolution) {
        this.cpaRepertory = cpaRepertory;
        this.cpaOptions = cpaOptions;
        this.cpaSolution = cpaSolution;
    }

    public CpaRepertory getCpaRepertory() {
        return cpaRepertory;
    }

    public void setCpaRepertory(CpaRepertory cpaRepertory) {
        this.cpaRepertory = cpaRepertory;
    }

    public List<CpaOption> getCpaOptions() {
        return cpaOptions;
    }

    public void setCpaOptions(List<CpaOption> cpaOptions) {
        this.cpaOptions = cpaOptions;
    }

    public CpaSolution getCpaSolution() {
        return cpaSolution;
    }

    public void setCpaSolution(CpaSolution cpaSolution) {
        this.cpaSolution = cpaSolution;
    }
}

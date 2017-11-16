package com.hse.vns.entity;

public class MoveAction {
    public VerticalShiftType verticalShift;
    public HorizontalShiftType horizontalShift;

    public MoveAction(VerticalShiftType verticalShift, HorizontalShiftType horizontalShift) {
        this.verticalShift = verticalShift;
        this.horizontalShift = horizontalShift;
    }
}

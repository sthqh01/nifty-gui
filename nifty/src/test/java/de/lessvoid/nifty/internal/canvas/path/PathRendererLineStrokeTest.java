package de.lessvoid.nifty.internal.canvas.path;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.lessvoid.nifty.api.NiftyRuntimeException;
import de.lessvoid.nifty.internal.canvas.LineParameters;
import de.lessvoid.nifty.internal.math.Mat4;
import de.lessvoid.nifty.internal.render.batch.BatchManager;

public class PathRendererLineStrokeTest {
  private PathRenderer pathRenderer;
  private LineParameters lineParameters;
  private Mat4 transform;
  private BatchManager batchManager;

  @Before
  public void before() {
    pathRenderer = new PathRenderer();
    lineParameters = new LineParameters();
    transform = new Mat4();
    batchManager = createMock(BatchManager.class);
  }

  @After
  public void after() {
    verify(batchManager);
  }

  @Test
  public void testBeginPathWithNoContent() {
    replayBatchManager();

    pathRenderer.beginPath();
  }

  @Test(expected = NiftyRuntimeException.class)
  public void testMoveToWithoutPath() {
    replayBatchManager();

    pathRenderer.moveTo(10, 10);
  }

  @Test
  public void testMoveToWithBeginPath() {
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.moveTo(10, 10);
  }

  @Test(expected = NiftyRuntimeException.class)
  public void testLineToWithoutPath() {
    replayBatchManager();

    pathRenderer.lineTo(100, 100);
  }

  @Test
  public void testLineToWithBeginPath() {
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.lineTo(100, 100);
  }

  @Test
  public void testLineToWithBeginPathAndMoveTo() {
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.moveTo(10, 10);
    pathRenderer.lineTo(100, 100);
  }

  @Test(expected = NiftyRuntimeException.class)
  public void testStrokePathWithoutPath() {
    replayBatchManager();

    pathRenderer.strokePath(null, null, null);
  }

  @Test
  public void testStrokeOfSimpleCorrectLine() {
    batchManager.addFirstLineVertex( 10.f,  10.f, transform, lineParameters);
    batchManager.addLineVertex(100.f, 100.f, transform, lineParameters);
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.moveTo(10, 10);
    pathRenderer.lineTo(100, 100);

    pathRenderer.strokePath(lineParameters, transform, batchManager);
  }

  @Test
  public void testStrokeOfTwoLines() {
    batchManager.addFirstLineVertex( 10.f,  10.f, transform, lineParameters);
    batchManager.addLineVertex(100.f, 100.f, transform, lineParameters);
    batchManager.addLineVertex(100.f, 200.f, transform, lineParameters);
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.moveTo(10, 10);
    pathRenderer.lineTo(100, 100);
    pathRenderer.lineTo(100, 200);

    pathRenderer.strokePath(lineParameters, transform, batchManager);
  }

  @Test
  public void testStrokeOfTwoWithMoveInBetweenLines() {
    batchManager.addFirstLineVertex( 10.f,  10.f, transform, lineParameters);
    batchManager.addLineVertex(100.f, 100.f, transform, lineParameters);
    batchManager.addFirstLineVertex(150.f, 150.f, transform, lineParameters);
    batchManager.addLineVertex(100.f, 200.f, transform, lineParameters);
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.moveTo(10, 10);
    pathRenderer.lineTo(100, 100);
    pathRenderer.moveTo(150, 150);
    pathRenderer.lineTo(100, 200);

    pathRenderer.strokePath(lineParameters, transform, batchManager);
  }

  @Test
  public void testStrokeOfLineWithoutMoveTo() {
    batchManager.addLineVertex(100.f, 100.f, transform, lineParameters);
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.lineTo(100, 100);

    pathRenderer.strokePath(lineParameters, transform, batchManager);
  }

  @Test
  public void testStrokeOfLineWithoutMoveToActualLine() {
    batchManager.addLineVertex(100.f, 100.f, transform, lineParameters);
    batchManager.addLineVertex(150.f, 150.f, transform, lineParameters);
    replayBatchManager();

    pathRenderer.beginPath();
    pathRenderer.lineTo(100, 100);
    pathRenderer.lineTo(150, 150);

    pathRenderer.strokePath(lineParameters, transform, batchManager);
  }

  private void replayBatchManager() {
    replay(batchManager);
  }
}
